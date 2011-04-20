package de.mpg.imeji.search;

import java.io.Serializable;

import javax.faces.event.ValueChangeEvent;

import de.mpg.imeji.collection.CollectionsSearchResultBean;
import de.mpg.imeji.image.ImagesBean;
import de.mpg.imeji.util.BeanHelper;

public class QuickSearchBean implements Serializable
{ 
    private String searchString;
    private String selectedSearchType = "images";

    public void setSearchString(String searchString)
    {
        this.searchString = searchString;
    }

    public String getSearchString()
    {
        return searchString;
    }

    public void setSelectedSearchType(String selectedSearchType)
    {
        this.selectedSearchType = selectedSearchType;
    }

    public String getSelectedSearchType()
    {
        return selectedSearchType;
    }
    
    public void selectedSearchTypeListener(ValueChangeEvent event)
    {
    	if (event.getNewValue() != null && !event.getNewValue().equals(event.getOldValue()))
    	{
    		selectedSearchType = (String) event.getNewValue();
    	}
    }
    
    
    public String search()
    {
    	if (getSelectedSearchType() == null) setSelectedSearchType("images");
    	
        if(getSelectedSearchType().equals("collections"))
        {
            CollectionsSearchResultBean bean = (CollectionsSearchResultBean)BeanHelper.getSessionBean(CollectionsSearchResultBean.class);
            bean.setQuery(searchString);
            return "pretty:collectionsSearchResults";
        }
        else if (getSelectedSearchType().equals("images"))
        {
            ImagesBean bean = (ImagesBean)BeanHelper.getSessionBean(ImagesBean.class);
            bean.setQuery("( ANY_METADATA=\"" + searchString +"\" )");
            return "pretty:images";
        }
        return "pretty:";
    }
    
}
