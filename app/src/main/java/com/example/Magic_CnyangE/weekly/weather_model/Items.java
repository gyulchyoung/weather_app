package com.example.Magic_CnyangE.weekly.weather_model;

public class Items
{
    private Item[] item;

    public Item[] getItem ()
    {
        return item;
    }

    public void setItem (Item[] item)
    {
        this.item = item;
    }

    @Override
    public String toString()
    {
        return "ClassPojo [item = "+item+"]";
    }
}
	