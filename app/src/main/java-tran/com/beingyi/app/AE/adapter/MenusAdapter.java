package com.beingyi.app.AE.adapter;

import com.beingyi.app.AE.application.MyApplication;
import android.content.*;
import android.view.*;
import android.widget.*;
import com.beingyi.app.AE.*;
import java.util.*;
public class MenusAdapter extends BaseAdapter
{

    Context context;
    ArrayList<String> menuList=new ArrayList<String>();
    String path;
    public MenusAdapter(Context context)
    {
        this.context = context;

    }
    
    
    

    public void setDate(ArrayList<String> data)
    {
        this.menuList=data;
    }
    
            
    
    public ArrayList<String> getDate()
    {
        return menuList;
    }



    @Override
    public int getCount()
    {
        if (menuList != null && menuList.size() > 0)
        {
            return menuList.size();

        }
        return 0;
    }
    @Override
    public Object getItem(int position)
    {
        if (menuList != null && menuList.size() > 0)
        {
            return menuList.get(position);
        }
        return null;
    }
    @Override
    public long getItemId(int position)
    {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent)
    {
        String  name=menuList.get(position);

        convertView = LayoutInflater.from(context).inflate(R.layout.item_menu, null);
        TextView menu=(TextView)convertView.findViewById(R.id.item_menu_TextView1);
        menu.setText(name);
        
        return (View)convertView;
    }






}


