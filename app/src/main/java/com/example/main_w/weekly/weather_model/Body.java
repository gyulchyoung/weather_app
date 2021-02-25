package com.example.main_w.weekly.weather_model;

public class Body
{
    private String pageNo;

    private String dataType;

    private String totalCount;

    private Items items;

    private String numOfRows;

    public String getPageNo ()
    {
        return pageNo;
    }

    public void setPageNo (String pageNo)
    {
        this.pageNo = pageNo;
    }

    public String getDataType ()
    {
        return dataType;
    }

    public void setDataType (String dataType)
    {
        this.dataType = dataType;
    }

    public String getTotalCount ()
    {
        return totalCount;
    }

    public void setTotalCount (String totalCount)
    {
        this.totalCount = totalCount;
    }

    public Items getItems ()
    {
        return items;
    }

    public void setItems (Items items)
    {
        this.items = items;
    }

    public String getNumOfRows ()
    {
        return numOfRows;
    }

    public void setNumOfRows (String numOfRows)
    {
        this.numOfRows = numOfRows;
    }

    @Override
    public String toString()
    {
        return "ClassPojo [pageNo = "+pageNo+", dataType = "+dataType+", totalCount = "+totalCount+", items = "+items+", numOfRows = "+numOfRows+"]";
    }
}
		