/**
 * License: src/main/resources/license/escidoc.license
 */

package de.mpg.imeji.presentation.beans;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.List;

import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;

import de.mpg.imeji.presentation.util.BeanHelper;
import de.mpg.imeji.presentation.util.PropertyReader;

/**
 * JavaBean for static content of the imeji solution.
 * 
 * @author saquet
 * 
 */

public class StaticContentBean {
	private boolean about = true;
	private boolean legal = true;
	private boolean blog = true;
	private boolean zuseZuse = true;
	private static Logger logger = Logger.getLogger(StaticContentBean.class);

	public StaticContentBean() throws IOException, URISyntaxException {
		if ("".equals(PropertyReader.getProperty("escidoc.imeji.about.url"))) {
			about = false;
		}

		if ("".equals(PropertyReader.getProperty("escidoc.imeji.legal.url"))) {
			legal = false;
		}

		if ("".equals(PropertyReader.getProperty("escidoc.imeji.blog.url"))) {
			blog = false;
		}

		if ("".equals(PropertyReader.getProperty("zuse.aboutZuse.url"))) {
			zuseZuse = false;
		}
		// sessionSize((HttpSession)
		// FacesContext.getCurrentInstance().getExternalContext().getSession(false));
	}

	public static int sessionSize(HttpSession session) {
		int total = 0;

		try {
			java.io.ByteArrayOutputStream baos = new java.io.ByteArrayOutputStream();
			java.io.ObjectOutputStream oos = new java.io.ObjectOutputStream(
					baos);
			Enumeration enumeration = session.getAttributeNames();
			List test = new ArrayList();

			while (enumeration.hasMoreElements()) {

				try {
					String name = (String) enumeration.nextElement();
					Object obj = session.getAttribute(name);
					oos.writeObject(obj);
					int size = baos.size();
					total += size;
					logger.info("The session name: " + name
							+ " and the size is: " + size);
				} catch (Exception e) {
					logger.error("Could not get the session size "
							+ e.getMessage());
				}

			}

			logger.info("Total session size is: " + total);
		} catch (Exception e) {
			logger.error("Could not get the session size", e);
			e.printStackTrace();
		}

		return total;
	}

	public String getHeaderLogo() {
		try {
			String html = "background-image: url( "
					+ PropertyReader.getProperty("escidoc.imeji.logo.url")
					+ ");";

			return html;
		} catch (Exception e) {
			return " ";
		}
	}

	public String getLogoLink() {
		try {
			return PropertyReader.getProperty("escidoc.imeji.logo.link.url");
		} catch (Exception e) {
			return "#";
		}
	}

	/**
	 * Get the HTML content of the Help page. URL of the Help page is defined in
	 * properties.
	 * 
	 * @return
	 * @throws URISyntaxException
	 * @throws IOException
	 */
	public String getHelpContent() throws IOException, URISyntaxException {
		String html = "";

		try {
			String helpProp = PropertyReader
					.getProperty("escidoc.imeji.help.url");
			html = getContent(new URL(helpProp));
		} catch (Exception e) {
			html = PropertyReader.getProperty("escidoc.imeji.help.url")
					+ " couldn't be loaded. Url might be either wrong or protected."
					+ "<br/><br/>" + "Error message:" + "<br/><br/>"
					+ e.toString();
		}

		return html;
	}

	/**
	 * Get the HTML content of the Home page. URL of the Home page is defined in
	 * properties.
	 * 
	 * @return
	 * @throws URISyntaxException
	 * @throws IOException
	 */
	public String getHomeContent() throws IOException, URISyntaxException {
		String html = "";

		try {
			html = getContent(new URL(
					PropertyReader.getProperty("escidoc.imeji.home.url")));
		} catch (Exception e) {
			html = PropertyReader.getProperty("escidoc.imeji.help.url")
					+ " couldn't be loaded. Url might be either wrong or protected."
					+ "<br/><br/>" + "Error message:" + "<br/><br/>"
					+ e.toString();
		}

		return html;
	}

	/**
	 * Get the HTML content of the About page. URL of the About page is defined
	 * in properties.
	 * 
	 * @return
	 * @throws URISyntaxException
	 * @throws IOException
	 */
	public String getAboutContent() throws IOException, URISyntaxException {
		String html = "";

		try {
			html = getContent(new URL(
					PropertyReader.getProperty("escidoc.imeji.about.url")));
		} catch (Exception e) {
			html = PropertyReader.getProperty("escidoc.imeji.help.url")
					+ " couldn't be loaded. Url might be either wrong or protected."
					+ "<br/><br/>" + "Error message:" + "<br/><br/>"
					+ e.toString();
		}

		return html;
	}

	/**
	 * Get the HTML content of the About page. URL of the About page is defined
	 * in properties.
	 * 
	 * @return
	 * @throws URISyntaxException
	 * @throws IOException
	 */
	public String getZuseContent() throws IOException, URISyntaxException {
		String html = "";

		SessionBean sb = (SessionBean)BeanHelper.getSessionBean(SessionBean.class);
        String language = sb.getLocale().getLanguage().toLowerCase();
        
        String languages = PropertyReader.getProperty("imeji.i18n.languages");
        
        if(language == null || language.isEmpty() || languages.isEmpty() || !languages.contains(language))
        	language = "en";
		
		try {
			
			String helpProp = PropertyReader.getProperty("escidoc.imeji.konradzusepage."+language+".url");
            html = getContent(new URL(helpProp));
			
		} catch (Exception e) {
			html = PropertyReader.getProperty("zuse.aboutZuse.url")
					+ " couldn't be loaded. Url might be either wrong or protected."
					+ "<br/><br/>" + "Error message:" + "<br/><br/>"
					+ e.toString();
		}

		return html;
	}
	
	/**
	 * Get the HTML content of the Legal page. URL of the Legal page is defined
	 * in properties.
	 * 
	 * @return
	 * @throws URISyntaxException
	 * @throws IOException
	 */
	public String getLegalContent() throws IOException, URISyntaxException {
		String html = "";

		try {
			html = getContent(new URL(
					PropertyReader.getProperty("escidoc.imeji.legal.url")));
		} catch (Exception e) {
			html = PropertyReader.getProperty("escidoc.imeji.help.url")
					+ " couldn't be loaded. Url might be either wrong or protected."
					+ "<br/><br/>" + "Error message:" + "<br/><br/>"
					+ e.toString();
		}

		return html;
	}

	public String getConfirmationContent() throws IOException,
			URISyntaxException {
		String html = "";

		try {
			html = getContent(new URL(
					PropertyReader
							.getProperty("escidoc.imeji.confirmation.url")));
		} catch (Exception e) {
			html = PropertyReader.getProperty("escidoc.imeji.confirmation.url")
					+ " couldn't be loaded. Url might be either wrong or protected."
					+ "<br/><br/>" + "Error message:" + "<br/><br/>"
					+ e.toString();
		}

		return html;
	}

	/**
	 * Get the html content of an {@link URL}
	 * 
	 * @param url
	 * @return
	 * @throws Exception
	 */
	private String getContent(URL url) throws Exception {
		BufferedReader in = new BufferedReader(new InputStreamReader(
				url.openStream()));

		String inputLine = "";
		String content = "";

		while (inputLine != null) {
			inputLine = in.readLine();
			if (inputLine != null) {
				content += inputLine + "  ";
			}
		}

		in.close();

		return content;
	}

	public boolean isAbout() {
		return about;
	}

	public void setAbout(boolean about) {
		this.about = about;
	}

	public boolean isLegal() {
		return legal;
	}

	public void setLegal(boolean legal) {
		this.legal = legal;
	}

	public boolean isBlog() {
		return blog;
	}

	public void setBlog(boolean blog) {
		this.blog = blog;
	}
	
	public boolean isZuseZuse() {
		return zuseZuse;
	}
	
	public void setZuse(boolean zuse) {
		this.zuseZuse = zuse;
	}

}
