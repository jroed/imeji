package de.mpg.imeji.image;

import java.util.ArrayList;
import java.util.List;

import javax.faces.event.ValueChangeEvent;
import javax.faces.model.SelectItem;

import de.mpg.imeji.album.AlbumBean;
import de.mpg.imeji.beans.Navigation;
import de.mpg.imeji.beans.SessionBean;
import de.mpg.imeji.metadata.extractors.BasicExtractor;
import de.mpg.imeji.util.BeanHelper;
import de.mpg.imeji.util.ProfileHelper;
import de.mpg.jena.controller.AlbumController;
import de.mpg.jena.controller.CollectionController;
import de.mpg.jena.controller.ImageController;
import de.mpg.jena.security.Operations.OperationsType;
import de.mpg.jena.security.Security;
import de.mpg.jena.vo.CollectionImeji;
import de.mpg.jena.vo.Image;
import de.mpg.jena.vo.Statement;

public class ImageBean
{
    public enum TabType
    {
        VIEW, EDIT, TECHMD;
    }

    private String tab = null;
    private SessionBean sessionBean = null;
    private Image image;
    private String id = null;
    private boolean selected;
    private ImageController imageController = null;
    private CollectionImeji collection;
    private CollectionController collectionController;
    private List<String> techMd;
    private Navigation navigation;
    protected String prettyLink;

    public ImageBean(Image img)
    {
        this.image = img;
        sessionBean = (SessionBean)BeanHelper.getSessionBean(SessionBean.class);
        navigation = (Navigation)BeanHelper.getApplicationBean(Navigation.class);
        imageController = new ImageController(sessionBean.getUser());
        collectionController = new CollectionController(sessionBean.getUser());
        prettyLink = "pretty:editImage";
        if (sessionBean.getSelected().contains(img.getId()))
        {
            setSelected(true);
        }
    }

    public ImageBean()
    {
        image = new Image();
        sessionBean = (SessionBean)BeanHelper.getSessionBean(SessionBean.class);
        navigation = (Navigation)BeanHelper.getApplicationBean(Navigation.class);
        imageController = new ImageController(sessionBean.getUser());
        collectionController = new CollectionController(sessionBean.getUser());
        prettyLink = "pretty:editImage";
    }

    public void init() throws Exception
    {
        try 
        {
        	image = imageController.retrieve(id);
		} 
        catch (Exception e) 
		{
			BeanHelper.error(id + " not found");
		}
        collection = collectionController.retrieve(this.getImage().getCollection());
        if (sessionBean.getSelected().contains(image.getId()))
        {
            setSelected(true);
        }
    } 

    public void initView() throws Exception
    {
        this.init();
        setTab(TabType.VIEW.toString());
    }

    public void initTechMd() throws Exception
    {
        this.init();
        setTab(TabType.TECHMD.toString());
    }

    public List<String> getTechMd() throws Exception
    {
    	techMd = BasicExtractor.extractTechMd(image);
    	return techMd;
    }

    public void setTechMd(List<String> md)
    {
        this.techMd = md;
    }

    public String getPageUrl()
    {
        return navigation.getApplicationUrl() + "image/" + this.id;
    }

    public String select()
    {
        if (!selected)
        {
            sessionBean.getSelected().remove(image.getId());
        }
        else
        {
            sessionBean.getSelected().add(this.image.getId());
        }
        return "";
    }

    public String clearAll()
    {
        sessionBean.getSelected().clear();
        return "pretty:";
    }

    public CollectionImeji getCollection()
    {
        return collection;
    }

    public void setCollection(CollectionImeji collection)
    {
        this.collection = collection;
    }

    public void setImage(Image image)
    {
        this.image = image;
    }

    public Image getImage()
    {
        return image;
    }

    /**
     * @param selected the selected to set
     */
    public void setSelected(boolean selected)
    {
        this.selected = selected;
    }

    public boolean getSelected()
    {
        if (sessionBean.getSelected().contains(image.getId()))
            selected = true;
        else
            selected = false;
        return selected;
    }

    public String getThumbnailImageUrlAsString()
    {
        return image.getThumbnailImageUrl().toString();
    }

    public String getId()
    {
        return id;
    }

    public void setId(String id)
    {
        this.id = id;
    }

    public String getTab()
    {
        return tab;
    }

    public void setTab(String tab)
    {
        this.tab = tab.toUpperCase();
    }

    protected String getNavigationString()
    {
        return "pretty:viewImage";
    }

    public SessionBean getSessionBean()
    {
        return sessionBean;
    }

    public void setSessionBean(SessionBean sessionBean)
    {
        this.sessionBean = sessionBean;
    }

    public String addToActiveAlbum()
    {
        AlbumBean activeAlbum = sessionBean.getActiveAlbum();
        AlbumController ac = new AlbumController(sessionBean.getUser());
        if (activeAlbum.getAlbum().getImages().contains(image.getId()))
        {
            BeanHelper.error("Image " + image.getFilename() + " already in active album!");
        }
        else
        {
            activeAlbum.getAlbum().getImages().add(image.getId());
            ac.update(activeAlbum.getAlbum());
            BeanHelper.info("Image " + image.getFilename() + " added to active album");
        }
        return "pretty:";
    }

    public boolean getIsInActiveAlbum()
    {
        if (sessionBean.getActiveAlbum() != null)
        {
            return sessionBean.getActiveAlbum().getAlbum().getImages().contains(image.getId());
        }
        return false;
    }

    public void selectedChanged(ValueChangeEvent event)
    {
        if (event.getNewValue().toString().equals("true") && !sessionBean.getSelected().contains(image.getId()))
        {
            setSelected(true);
            select();
        }
        else if (event.getNewValue().toString().equals("false") && sessionBean.getSelected().contains(image.getId()))
        {
            setSelected(false);
            select();
        }
    }
    
    public List<SelectItem> getStatementMenu()
    {
    	List<SelectItem> statementMenu = new ArrayList<SelectItem>();
        for (Statement s : ProfileHelper.loadProfile(image).getStatements())
        {
        	 statementMenu.add(new SelectItem(s.getName(), s.getName()));
        }
    	return statementMenu;
    }

	public boolean isEditable() 
	{
		Security security = new Security();
		return security.check(OperationsType.UPDATE, sessionBean.getUser(), image);
	}
	
	public boolean isVisible() 
	{
		Security security = new Security();
		return security.check(OperationsType.READ, sessionBean.getUser(), image);
	}
	
	public boolean isDeletable() 
	{
		Security security = new Security();
		return security.check(OperationsType.DELETE, sessionBean.getUser(), image);
	}
}
