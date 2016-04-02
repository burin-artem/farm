package ru.farm.operator.view;

import ru.farm.operator.service.UserService;

import java.io.Serializable;

import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

@ManagedBean(name="dtLoginView")
@SessionScoped
public class LoginView implements Serializable {
	private static final long serialVersionUID = 1L;
	private String username;
	private String password;
	private String message;

	@ManagedProperty("#{userService}")
	private UserService service;

	@PostConstruct
	public void init() {
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public UserService getService() {
		return service;
	}

	public void setService(UserService service) {
		this.service = service;
	}

	public String loginProject() {
		boolean result = service.login(username, password);
		if (result) {
			// get Http Session and store username
			HttpSession session = getSession();
			session.setAttribute("username", username);

			return "index";
		} else {

			FacesContext.getCurrentInstance().addMessage(
					null,
					new FacesMessage(FacesMessage.SEVERITY_WARN,
							"Данные введены неправильно!",
							"Попробуйте ещё раз!"));

			// invalidate session, and redirect to other pages

			//message = "Invalid Login. Please Try Again!";
			return "login";
		}
	}

	public String logout() {
		HttpSession session = getSession();
		session.invalidate();
		return "login";
	}

	public static HttpSession getSession() {
		return (HttpSession)FacesContext.getCurrentInstance().getExternalContext().getSession(false);
	}

	public static HttpServletRequest getRequest() {
		return (HttpServletRequest) FacesContext.
				getCurrentInstance().
				getExternalContext().getRequest();
	}

	public static String getUserName()
	{
		HttpSession session = (HttpSession) FacesContext.getCurrentInstance().getExternalContext().getSession(false);
		return session.getAttribute("username").toString();
	}

	public static String getUserId()
	{
		HttpSession session = getSession();
		if ( session != null )
			return (String) session.getAttribute("userid");
		else
			return null;
	}
}
