package com.microfocus.ucmdb.server.fakedata.cache;

import org.apache.log4j.Logger;

import java.io.*;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class CreatedCICache {
    final Logger logger = Logger.getLogger(CreatedCICache.class);
    final static public String filename = "cache.txt";
    final private String LINE_SEPARATOR = ",";
    private Map<String, CICacheItem> cachedItems;
    private static CreatedCICache _instance = null;

    public Map<String, CICacheItem> getCachedItems() {
        if(cachedItems == null){
            cachedItems = new HashMap<String, CICacheItem>();
        }
        return cachedItems;
    }

    public static CreatedCICache getInstance(){
        if(_instance == null){
            _instance = new CreatedCICache();
        }
        return _instance;
    }

    private CreatedCICache() {
        loadCache();
    }

    public void clearCache(){
        getCachedItems().clear();
        File file = new File(filename);
        if(file.exists()){
            file.delete();
        }
    }

    public void loadCache(){
        File file = new File(filename);
        if(!file.exists()){
            return;
        }
        BufferedReader reader = null;
        try {
            reader = new BufferedReader(new FileReader(file));
            String tempString = null;
            while ((tempString = reader.readLine()) != null) {
                String[] splits = tempString.split(LINE_SEPARATOR);
                addCacheItem(splits[1], splits[0]);
            }
            reader.close();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (reader != null) {
                try {
                    reader.close();
                } catch (IOException e1) {
                }
            }
        }

    }

    public void saveCache(){
        File file = new File(filename);
        if(!file.exists()){
            try {
                file.createNewFile();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        BufferedWriter writer = null;
        try {
            writer = new BufferedWriter(new FileWriter(file));
            for(CICacheItem ci : cachedItems.values()){
                writer.write(ci.getGlobalId() + LINE_SEPARATOR + ci.getClassType());
                writer.newLine();
            }
            writer.close();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (writer != null) {
                try {
                    writer.close();
                } catch (IOException e1) {
                }
            }
        }
    }

    public void addCacheList(List<CICacheItem> cacheItemList){

    }

    public void addCacheItem(String type, String id)
    {
        CICacheItem item = new CICacheItem();
        item.setClassType(type);
        item.setGlobalId(id);
        getCachedItems().put(id, item);
    }


}
