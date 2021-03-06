package de.mpg.imeji.logic.search.vo;

import java.util.List;

public class SearchLogicalRelation extends SearchElement
{
    public enum LOGICAL_RELATIONS
    {
        AND, OR;
    }
    
    private LOGICAL_RELATIONS logicalRelation;
    
    public SearchLogicalRelation(LOGICAL_RELATIONS lr)
    {
        setLogicalRelation(lr);
    }

    public void setLogicalRelation(LOGICAL_RELATIONS logicalRelation)
    {
        this.logicalRelation = logicalRelation;
    }

    public LOGICAL_RELATIONS getLogicalRelation()
    {
        return logicalRelation;
    }

    @Override
    public SEARCH_ELEMENTS getType()
    {
        return SEARCH_ELEMENTS.LOGICAL_RELATIONS;
    }

    @Override
    public List<SearchElement> getElements()
    {
        return null;
    }
}
