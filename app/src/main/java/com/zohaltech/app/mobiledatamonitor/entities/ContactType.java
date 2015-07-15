package com.zohaltech.app.mobiledatamonitor.entities;

public class ContactType
{
    private long   id;
    private String name;

    public ContactType(String name) {
        this.name = name;
    }

    public ContactType(long id, String name)
    {
        this(name);
        this.id = id;
    }

    public long getId()
    {
        return id;
    }

    public void setId(long value)
    {
        id = value;
    }

    public String getName()
    {
        return name;
    }

    public void setName(String value)
    {
        name = value;
    }

}
