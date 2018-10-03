package main.java.com.devrevolhope.mywallet.controller;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.MessageSource;
import org.springframework.security.authentication.AuthenticationTrustResolver;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.rememberme.PersistentTokenBasedRememberMeServices;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.servlet.ModelAndView;

import main.java.com.devrevolhope.mywallet.model.Account;
import main.java.com.devrevolhope.mywallet.model.AppUser;
import main.java.com.devrevolhope.mywallet.model.AppUserRole;
import main.java.com.devrevolhope.mywallet.model.Directory;
import main.java.com.devrevolhope.mywallet.model.SharedAccount;
import main.java.com.devrevolhope.mywallet.service.AccountService;
import main.java.com.devrevolhope.mywallet.service.DirectoryService;
import main.java.com.devrevolhope.mywallet.service.SharedAccountService;
import main.java.com.devrevolhope.mywallet.service.UserRoleService;
import main.java.com.devrevolhope.mywallet.service.UserService;

@Controller
@RequestMapping("/")
@SessionAttributes("roles")
public class MyController {

	// ==================================================================== //
	// 								AUTOWIRED FIELDS
	// ==================================================================== //
	
	/// :..............: SERVICES :..............:
	
	@Autowired
	UserService userService;
	
	@Autowired
	UserRoleService userRoleService;

	@Autowired
	DirectoryService directoryService;
	
	@Autowired
	AccountService accountService;
	
	@Autowired
	SharedAccountService sharedAccountService;
	
	@Autowired(required=true)
	@Qualifier(value="userService")
	public void setUserService(UserService userService) {
		this.userService = userService;
	}

	@Autowired(required=true)
	@Qualifier(value="userRoleService")
	public void setUserRoleService(UserRoleService userRoleService) {
		this.userRoleService = userRoleService;
	}
	
	@Autowired(required=true)
	@Qualifier(value="directoryService")
	public void setDirectoryService(DirectoryService directoryService) {
		this.directoryService = directoryService;
	}
	
	@Autowired(required=true)
	@Qualifier(value="accountService")
	public void setAccountService(AccountService accountService) {
		this.accountService = accountService;
	}
	
	@Autowired(required=true)
	@Qualifier(value="sharedAccountService")
	public void setSharedAccountService(SharedAccountService sharedAccountService) {
		this.sharedAccountService = sharedAccountService;
	}
	
	/// :..............: SECURITY :..............:
	
	@Autowired
	PersistentTokenBasedRememberMeServices persistentTokenBasedRememberMeServices;

	@Autowired
	AuthenticationTrustResolver authenticationTrustResolver;
	
	/// :..............: MESSAGE SOURCE :..............:
	
	@Autowired
	MessageSource messageSource;
	
	
	// ==================================================================== //
	// 							MODEL ATTRIBUTES
	// ==================================================================== //
	
	public static AppUser currentUser;
	public static Directory currentDir;
	public static Directory currentDirMoveModal;
	public static AppUser currentShareUser;
	public static Map<Long,List<SharedAccount>> mapSharings;
	
	public List<String> pathNames = new ArrayList<>();
	
	@ModelAttribute("appUser")
	public AppUser getAppUser() 
	{
		return new AppUser();
	}
	
	@ModelAttribute("roles")
	public List<AppUserRole> initializeProfiles() 
	{
		return userRoleService.findAll();
	}
	
	@ModelAttribute("directory")
	public Directory getDirectory() 
	{
		return new Directory();
	}
	
	@ModelAttribute("account")
	public Account getAccount() 
	{
		return new Account();
	}

	// ==================================================================== //
	// 								MAPPINGS
	// ==================================================================== //
	
	/// :..............: GETTERS :..............:
	
	@GetMapping(value = {"/"})
	public ModelAndView getRoot(HttpServletRequest request, HttpServletResponse response, ModelMap model) throws ServletException, IOException 
	{
		if (isCurrentAuthenticationAnonymous())
		{
			return new ModelAndView("login");
		} 
		else
		{
			if (currentUser == null)
			{
				String userName = getPrincipal();
				AppUser user = userService.findByName(userName);
				if (user != null)
				{
					currentUser = user;
				}
				else
				{
					// TODO:
					//return new ModelAndView(noUserFound);
				}
			}
			
			if (currentUser != null)
			{
				List<Directory> directories = new ArrayList<>();
				List<Account> accounts = new ArrayList<>();
				Map<Long, Integer> counter = new HashMap<>();
				Map<Long, Boolean> accntShared = new HashMap<>();
				
				String p = request.getParameter("d");
				if (p != null && !p.equals("Home"))
				{
					currentDir = directoryService.findByName(p, currentUser.getId());
				}
				else if (p != null && p.equals("Home"))
				{
					currentDir = null;
				}
				
				pathNames.clear();
				
				if (currentDirMoveModal == null)
				{
					Directory root = directoryService.findRoot(currentUser.getId());
					model.addAttribute("modalMove",root.getChildren());
				}
				else
				{
					currentDirMoveModal = directoryService.findById(currentDirMoveModal.getId());
					List<Directory> move = new ArrayList<>();
					move.addAll(currentDirMoveModal.getChildren());
					model.addAttribute("modalMove",move);
				}
				
				if (currentDir == null)
				{
					Directory root = directoryService.findRoot(currentUser.getId());
					accounts.addAll(root.getAccounts());
					
					for (Account a : accounts)
					{
						accntShared.put(a.getId(), sharedAccountService.isAccountShared(a.getId(), currentUser.getId()));
					}
					
					directories.addAll(root.getChildren());
					
					Directory d1;
					for (Directory d : directories)
					{
						d1 = directoryService.findById(d.getId());
						counter.put(d.getId(), d1.getChildren().size()+d1.getAccounts().size());
					}
					
					pathNames.add("Home");
				}
				else
				{
					accounts.addAll(currentDir.getAccounts());
					directories.addAll(currentDir.getChildren());
					for (Account a : accounts)
					{
						accntShared.put(a.getId(), sharedAccountService.isAccountShared(a.getId(), currentUser.getId()));
					}
					
					Directory d1;
					for (Directory d : directories)
					{
						d1 = directoryService.findById(d.getId());
						if (d1 != null)
							counter.put(d.getId(), d1.getChildren().size()+d1.getAccounts().size());
					}
					
					Directory d = currentDir;
					try
					{
						do {
							if(!d.getName().equals("HOME"))
							{
								pathNames.add(0, d.getName());
								d = d.getParent();
							}
							else
							{
								pathNames.add(0, "Home");
								break;
							}
						} while(d != null);
					}
					catch(Exception e)
					{
						e.printStackTrace();
					}
				}
				
				model.addAttribute("paths", pathNames);
				model.addAttribute("curr_d", currentDir != null ? currentDir.getName() : "Home" );
				
				model.addAttribute("accnts", accounts);
				model.addAttribute("dirs", directories);
				model.addAttribute("counter", counter);
				model.addAttribute("accntShared", accntShared);
			}
			
			return new ModelAndView("main");  
		}
	}
		
	@GetMapping(value = "/login")
    public ModelAndView getLogin(ModelMap model) 
	{
		return new ModelAndView("login");
    }

	@GetMapping(value = "/AccessDenied")
    public ModelAndView getAccessDeniedPage(ModelMap model) 
	{
        return new ModelAndView("hello");
    }
	
	@GetMapping(value = "/getItem")
	public void getItem(HttpServletRequest request, HttpServletResponse response, ModelMap model) throws ServletException, IOException
	{
		String type = request.getParameter("type");
		String id = request.getParameter("id");
		
		long longId = Long.parseLong(id);
		response.setContentType("text/plain");
		PrintWriter out = response.getWriter();
		
		if (type != null && type.equals("dir"))
		{
			Directory d = directoryService.findById(longId);
			out.write(d.getId() + "$$" + d.getName());
		}
		else if(type != null && type.equals("accnt"))
		{
			Account a = accountService.findById(longId);
			out.write(a.getId() + "$$"
					+ a.getName() + "$$" 
					+ a.getEmail() + "$$"
					+ a.getDescription() + "$$"
					+ a.getKey() + "$$"
					+ a.getMetadata());
		}
	}
		
	@GetMapping(value = "/move")
	public void getMove(HttpServletRequest request, HttpServletResponse response, ModelMap model) throws IOException
	{
		String name = request.getParameter("dir");
		try
		{
			Directory d = directoryService.findByName(name, currentUser.getId());
			PrintWriter w = response.getWriter();
			String data = "";
			currentDirMoveModal = d;
			for (Directory dir : d.getChildren())
			{
				dir = directoryService.findById(dir.getId());
				data += dir.getName() + "__" + dir.getId() + "__" + (dir.getChildren().size() + dir.getAccounts().size()) + "___";
			}
			if (data.equals(""))
			{
				data = "NoData";
			}
			w.write(data);
		}
		catch(NumberFormatException ignored)
		{
		}
	}
	
	@GetMapping(value = "/moveBack")
	public void getMoveBack(HttpServletRequest request, HttpServletResponse response, ModelMap model) throws IOException
	{
		if (currentDirMoveModal != null && !currentDirMoveModal.getName().equals("Home"))
		{
			Directory d = currentDirMoveModal.getParent();
			PrintWriter w = response.getWriter();
			if (d != null)
				w.write(d.getName());
		}
	}
	
	@GetMapping(value = {"/shared"})
	public ModelAndView getSharedUsers(HttpServletRequest request, HttpServletResponse response, ModelMap model) throws ServletException, IOException
	{
		if (currentUser == null)
		{
			String userName = getPrincipal();
			AppUser user = userService.findByName(userName);
			if (user != null)
			{
				currentUser = user;
			}
			else
			{
				// TODO:
				//return new ModelAndView(noUserFound);
			}
		}
		
		if (currentUser != null)
		{
			mapSharings = sharedAccountService.findAllSharings(currentUser.getId());
			Map<Long, Integer> counter = new HashMap<>();
			Map<Long, Long> dates = new HashMap<>();
			List<AppUser> usrs = new ArrayList<>();
			long maxDate = 0;
			
			for (long uId : mapSharings.keySet())
			{
				for (SharedAccount sa : mapSharings.get(uId))
				{
					if (sa.getDateSharing() > maxDate)
					{
						maxDate = sa.getDateSharing();
					}
				}
				usrs.add(userService.findById(uId));
				dates.put(uId, maxDate);
				counter.put(uId, mapSharings.get(uId).size());
			}
			
			model.addAttribute("usrs", usrs);
			model.addAttribute("dates", dates);
			model.addAttribute("counts", counter);
		}
		
		return new ModelAndView("sharedUsers");
	}

	@GetMapping(value = {"/share/accnts"})
	public ModelAndView getSharedAccounts(HttpServletRequest request, HttpServletResponse response, ModelMap model) throws ServletException, IOException
	{
		if (currentUser == null)
		{
			String userName = getPrincipal();
			AppUser user = userService.findByName(userName);
			if (user != null)
			{
				currentUser = user;
			}
			else
			{
				// TODO:
				//return new ModelAndView(noUserFound);
			}
		}
		String idStr = request.getParameter("id");
		try 
		{
			long id = Long.parseLong(idStr);
			currentShareUser = userService.findById(id);
		}
		catch (NumberFormatException e) 
		{
			e.printStackTrace();
		}
		
		if (currentUser != null && mapSharings != null && currentShareUser != null)
		{
			List<SharedAccount> list = mapSharings.get(currentShareUser.getId());
			
			model.addAttribute("accounts", list);
			model.addAttribute("idUser",currentUser.getId());
		}
		
		return new ModelAndView("sharedAccounts");
	}
	
	/// :..............: POSTS :..............:

	@PostMapping(value = {"/addAccount"})
	public ModelAndView addAccount (@Validated Account account,
									BindingResult result,
									@RequestParam("lengthAccount") String length,
									@RequestParam("controlSelectComplexity") String complexity,
									ModelMap model)
	{
		
		if (result.hasErrors())
		{
			return new ModelAndView("main");
		}
		
		if (currentDir == null)
		{
			currentDir = directoryService.findRoot(currentUser.getId());
			
		}
		account.setDirectory(currentDir);
		
		if (length == null || length.isEmpty()) length = ""+account.getKey().length();
		if (complexity == null || complexity.isEmpty())
		{
			complexity = getComplexityFromKey(account.getKey());
		}
		
		account.setCreationDate(System.currentTimeMillis());
		account.setMetadata(length+"."+complexity);
		
		accountService.persist(account);
		
		return new ModelAndView("redirect:/");
	}
	
	@PostMapping(value = {"/addDir"})
	public ModelAndView addDirectory(	@Validated Directory dir,
										BindingResult result,
										ModelMap model)
	{
		
		if (result.hasErrors()) 
		{
			return new ModelAndView("main");
        }
		
		try
		{
			dir.setOwner(currentUser);
			if (currentDir == null)
			{
				currentDir = directoryService.findRoot(currentUser.getId());
			}
			else
			{
				currentDir = directoryService.findById(currentDir.getId()); // To update fetched children
			}
			
			dir.setParent(currentDir);
			directoryService.persist(dir);
			
			return new ModelAndView("redirect:/");
		}
		catch(Exception ignored)
		{
			System.out.println(ignored.getMessage());
			return new ModelAndView("main");
		}
	}
	
	@PostMapping(value = {"/editDir"})
	public ModelAndView editDirectory(	@Validated Directory dir,
										BindingResult result,
										HttpServletRequest request, HttpServletResponse response,
										ModelMap model) throws ServletException, IOException
	{
		
		if (result.hasErrors()) 
		{
			return new ModelAndView("main");
        }
		
		try
		{
			String idString = request.getParameter("idDirectory");
			long id = Long.parseLong(idString);
			
			Directory d = directoryService.findById(id);
			d.setName(dir.getName());
			
			directoryService.update(d);
			
			return new ModelAndView("redirect:/");
		}
		catch(Exception ignored)
		{
			System.out.println(ignored.getMessage());
			return new ModelAndView("main");
		}
	}
	
	@PostMapping(value = {"/editAccnt"})
	public ModelAndView editAccount(	@Validated Account account,
										BindingResult result,
										HttpServletRequest request, HttpServletResponse response,
										ModelMap model) throws ServletException, IOException
	{
		
		if (result.hasErrors()) 
		{
			return new ModelAndView("main");
        }
		
		try
		{
			String idString = request.getParameter("idAccount");
			String length = request.getParameter("lengthAccount");
			String complexity = request.getParameter("controlSelectComplexity");
			
			long id = Long.parseLong(idString);
			
			Account a = accountService.findById(id);
			
			a.setName(account.getName());
			a.setEmail(account.getEmail());
			a.setDescription(account.getDescription());
			a.setKey(account.getKey());
			a.setUpdatedDate(System.currentTimeMillis());
			
			if (length != null && complexity != null)
			{
				a.setMetadata(length + "." + complexity);
			}
			else
			{
				a.setMetadata("");
			}
			
			
			accountService.update(a);
			
			return new ModelAndView("redirect:/");
		}
		catch(Exception ignored)
		{
			System.out.println(ignored.getMessage());
			return new ModelAndView("main");
		}
	}
	
	@PostMapping(value = {"/deleteItem"})
 	public ModelAndView deleteItem(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException 
	{
		String idText = request.getParameter("itemToDelete");
		String type = request.getParameter("typeToDelete");
		try
		{
			long id = Long.parseLong(idText);

			if (type != null && type.equals("accnt"))
			{
				accountService.remove(accountService.findById(id));
			}
			else
			{
				directoryService.remove(directoryService.findById(id));
			}
		}
		catch(NumberFormatException ignored)
		{}
		
		return new ModelAndView("redirect:/");
	}
	
	@PostMapping(value = {"/getInto"})
 	public ModelAndView postGetInto(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException 
	{
		String idNextDir = request.getParameter("dir");
		try
		{
			long id = Long.parseLong(idNextDir);
			currentDir = directoryService.findById(id);
		}
		catch(NumberFormatException ignored)
		{
			
		}
		
		return new ModelAndView("redirect:/");
	}

	@PostMapping(value = {"/moveSubmit"})
 	public ModelAndView postMove(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException 
	{
		String id = request.getParameter("id");
		String type = request.getParameter("type");
		
		if (id != null && type != null && !id.isEmpty() && !type.isEmpty())
		{
			try 
			{
				long longId = Long.parseLong(id);
				if (type.equals("dir"))
				{
					Directory d = directoryService.findById(longId);
					d.setParent(currentDirMoveModal);
					directoryService.update(d);
				}
				else
				{
					Account a = accountService.findById(longId);
					a.setDirectory(currentDirMoveModal);
					accountService.update(a);
				}
			}
			catch (NumberFormatException ignored)
			{
				
			}
		}
		
		return new ModelAndView("redirect:/");
	}
	
	@PostMapping(value = {"/register"})
	public ModelAndView postRegister(
							@Validated AppUser user,
							BindingResult result,
							@RequestParam("password2") String pwd,
							ModelMap model)
	{
		
		if (result.hasErrors()) 
		{
			return new ModelAndView("login");
        }
		else
		{
			if (!user.getPassword().equals(pwd))
			{
				FieldError nonUniqueError = new FieldError("user", "password",
						messageSource.getMessage("NonMatch.user.password2", null, Locale.getDefault()));
				result.addError(nonUniqueError);
				return new ModelAndView("login");
			}
			else if(userService.findByName(user.getName()) != null) 
			{
				FieldError nonUniqueError = new FieldError("user", "name",
						messageSource.getMessage("NonUnique.user.name",
												new String[] { user.getName() }  , Locale.getDefault()));
				result.addError(nonUniqueError);
				return new ModelAndView("login");
			}
			else
			{
				for (AppUserRole role : userRoleService.findAll())
				{
					if (role.getType().equals("ADMIN"))
					{
						user.getUserRoles().add(role);
						break;
					}
				}
				//userService.persist(user);
				model.addAttribute("appUser", user);
				return new ModelAndView("registerSucceed");
			}
		}
	}
	
	
	// ==================================================================== //
	// 							OTHER FUNCTIONS
	// ==================================================================== //
	

	@PostMapping(value = {"/shareAccnt"})
 	public ModelAndView postShareAccnt(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException 
	{
		String nameUsr = request.getParameter("name");
		String idAccntStr = request.getParameter("idAccnt");
		
		try {
			long idAccnt = Long.parseLong(idAccntStr);
			AppUser user = null;
			for (AppUser usr : userService.findAll())
			{
				if(usr.getName() != null && usr.getName().equals(nameUsr))
				{
					user = usr;
				}
			}
			Account a = accountService.findById(idAccnt);
			if (user != null && a != null && currentUser != null)
			{
				SharedAccount sa = new SharedAccount();
				sa.setAccountOwner(currentUser);
				sa.setUserShared(user);
				sa.setAccountShared(a);
				sa.setDateSharing(System.currentTimeMillis());
				sharedAccountService.persist(sa);
				return new ModelAndView("redirect:/shared");
			}
			else
			{
				
			}
		}
		catch(NumberFormatException e)
		{
			e.printStackTrace();
		}
		return new ModelAndView("redirect:/");
	}
	
	
	
	
	/**
	 *  This method returns the complexity of the key (see main.jsp-> modals)
	 */	
	private String getComplexityFromKey(String key)
	{
		String numbers = "1234567890";
		int indx = 0;
		for (char c : key.toCharArray())
		{
			if (numbers.contains(Character.toString(c)))
			{
				indx++;
			}
		}
		if (indx == key.length()) return "12";
		else if (indx != 0) return "23";
		else return "37";
	}
	
	/**
     * This method returns the principal[user-name] of logged-in user.
     */	
	private String getPrincipal(){
        String userName = null;
        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
 
        if (principal instanceof UserDetails) {
            userName = ((UserDetails) principal).getUsername();
        } else {
            userName = principal.toString();
        }
        return userName;
    }
    
     /**
	 * This method returns true if users is already authenticated [logged-in], else false.
	 */
	private boolean isCurrentAuthenticationAnonymous() 
	{
		final Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		return authenticationTrustResolver.isAnonymous(authentication);
	}
}
