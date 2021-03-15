package com.beingyi.app.AE.activity;

import com.beingyi.app.AE.utils.AesUtil;
import com.beingyi.app.AE.utils.BASE128;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.TextView;
import java.io.ByteArrayOutputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.AdapterView;
import android.widget.Adapter;

import com.beingyi.app.AE.R;
import com.beingyi.app.AE.base.BaseActivity;
import com.beingyi.app.AE.utils.BYProtectUtils;


public class OpenSourceList extends BaseActivity
{
    
    Context context;
    ListView listview;

    List<OSInfo> osList=new ArrayList<>();
    OSAdapter adapter;
    
    public void init(){
        context=this;
        listview=findViewById(R.id.activity_opensourcelist_ListView);
        
        adapter=new OSAdapter(context);
    }
    
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_opensourcelist);
        init();
        
        String content=BYProtectUtils.readAssetsTxt(BASE128.decode("w+BgWaeQq3yYPpKhSOiKyU3xKcEhBdXa8phWLVtMPNQ="));
        
        String[] osArray=content.split("#\n#\n#\n");
        

        
        for(int i=0;i<osArray.length;i++){
            
            String info=osArray[i];
            String[] s=info.split("\n");
            
            OSInfo osInfo=new OSInfo();
            osInfo.Name=s[0];
            osInfo.License=s[2];
            osInfo.Url=s[1];
            osInfo.Description=s[3];
            
            osList.add(osInfo);
            
            
        }
        
        adapter.setData(osList);
        listview.setAdapter(adapter);
        
        listview.setOnItemClickListener(new OnItemClickListener(){

                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id)
                {
                    openUrl(osList.get(position).Url);
                    
                }
                
            
        });
        
    }
    
    
    
    
    
    


    public class OSAdapter extends BaseAdapter
    {


        Context context;
        public List<OSInfo> osList;
        public String Path;

        public OSAdapter(final Context context)
        {
            this.context = context;
            this.osList = new ArrayList<OSInfo>();
            
        }


        public void setData(List<OSInfo> osList)
        {

            this.osList = osList;
            notifyDataSetChanged();

        }


        @Override
        public int getCount()
        {
            if (osList != null && osList.size() > 0)
            {
                return osList.size();
            }
            return 0;
        }

        @Override
        public Object getItem(int position)
        {
            if (osList != null && osList.size() > 0)
            {
                return osList.get(position);
            }
            return null;
        }

        @Override
        public long getItemId(int position)
        {
            return position;
        }

        @Override
        public boolean hasStableIds()
        {
            return true ;
        }



        public List<OSInfo> getData()
        {
            return osList;
        }



        @Override
        public View getView(int position, View convertView, ViewGroup parent)
        {

            OSHolder holder=null;
            if (convertView == null)
            {
                convertView = LayoutInflater.from(context).inflate(R.layout.item_opensource, null);
                holder = new OSHolder();
                
                holder.Name = (TextView)convertView.findViewById(R.id.item_open_source_TextView_Name);
                holder.License = (TextView)convertView.findViewById(R.id.item_open_source_TextView_License);
                holder.Url = (TextView)convertView.findViewById(R.id.item_open_source_TextView_Url);
                holder.Description = (TextView)convertView.findViewById(R.id.item_open_source_TextView_Description);

                convertView.setTag(holder);


            }
            else
            {
                holder = (OSHolder) convertView.getTag();
            }



            OSInfo OSInfo=osList.get(position);
            
            holder.Name.setText(OSInfo.Name);
            holder.License.setText(OSInfo.License);
            holder.Url.setText(OSInfo.Url);
            holder.Description.setText(OSInfo.Description);
            
            
            return convertView;
        }

        
    }
    
    class OSHolder{
        public TextView Name;
        public TextView License;
        public TextView Url;
        public TextView Description;

    }
    
    
    class OSInfo{
        public String Name="";
        public String Url="";
        public String License="";
        public String Description="";
        
    }
    
    

    public void openUrl(String url) {
        Uri uri = Uri.parse(url);
        Intent intent = new Intent(Intent.ACTION_VIEW, uri);
        context.startActivity(intent);
    }
    
}
