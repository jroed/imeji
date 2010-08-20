package de.mpg.imeji.collection;

import de.mpg.imeji.util.BeanHelper;

public class EditCollectionBean extends CollectionBean
{
    public EditCollectionBean()
    {
        // TODO Auto-generated constructor stub
    }

    public void init()
    {
    }

    public void save() throws Exception
    {
        if (valid())
        {
            collectionController.update(collection);
            BeanHelper.info("collection_success_create");
        }
    }

    public void next()
    {
        switch (tab)
        {
            case PROFILE:
                tab = TabType.HOME;
                break;
            case COLLECTION:
                tab = TabType.PROFILE;
                break;
            default:
                break;
        }
    }

    public void back()
    {
        switch (tab)
        {
            case HOME:
                tab = TabType.PROFILE;
                break;
            case PROFILE:
                tab = TabType.COLLECTION;
                break;
            default:
                break;
        }
    }

    @Override
    protected String getNavigationString()
    {
        return "pretty:editCollection";
    }
}
