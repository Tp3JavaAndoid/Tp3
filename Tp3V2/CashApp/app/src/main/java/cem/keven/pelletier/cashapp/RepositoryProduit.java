package cem.keven.pelletier.cashapp;

import android.content.Context;

import com.google.gson.Gson;

import org.apache.commons.io.FileUtils;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.*;

/**
 * Created by kinpell on 23/04/2015.
 */
public class RepositoryProduit implements CRUD<Produit> {

    Gson gson = new Gson();

    Class<Produit> classe = Produit.class;

    Context context;

    private static RepositoryProduit one;


    private   RepositoryProduit(Context c){
        this.context =c;
        this.createStorage();
    }

    public static synchronized RepositoryProduit get(Context context) {
        if (one == null) one = new RepositoryProduit(context);
        return one;
    }

    public static synchronized RepositoryProduit get() {
        if (one == null) throw new NullPointerException();
        return one;
    }

    public static synchronized void release() {
        one = null;
    }

    public List<Produit> getAll() {
        synchronized (classe) {
            List<Produit> res = new ArrayList<Produit>();
            File base = context.getFilesDir();
            for (File f : base.listFiles()){
                try{
                    //System.out.println("File is "+f.getName());
                    String content = FileUtils.readFileToString(f);
                    Produit a = gson.fromJson(content, classe);
                    res.add(a);
                }
                catch(Exception e){
                    e.printStackTrace();
                }
            }
            return res;
        }
    }

    @Override
    public void deleteOne(Long o) {
        this.deleteOne(this.getById(o));
    }

    public long save(Produit a) {
        synchronized (classe) {
            // set the id
            if (a.getId() == null) a.setId(this.nextAvailableId());
            //
            String serialise = gson.toJson(a);
            File base = context.getFilesDir();
            try {
                FileUtils.writeStringToFile(new File(base, a.getId()+".produit"), serialise);
            } catch (IOException e) {
                e.printStackTrace();
            }
            return a.getId();

        }
    }

    @Override
    public void saveMany(Iterable<Produit> list) {
        for (Produit p : list ){
            this.save(p);
        }
    }

    @Override
    public void saveMany(Produit... list) {
        for (Produit p : list ){
            this.save(p);
        }
    }

    @Override
    public Produit getById(Long id) {
        synchronized (classe) {
            String content;
            try {
                File base = context.getFilesDir();
                File f = new File(base,id+".produit");
                if (!f.exists()) return null;
                content = FileUtils.readFileToString(new File(base,id+".produit"));
                Produit a = gson.fromJson(content, classe);
                return a;
            } catch (IOException e) {
                return null;
            }
        }
    }

    public void deleteOne(Produit a) {
        synchronized (classe) {
            File base = context.getFilesDir();
            File f = new File(base, a.getId()+".produit");
            f.delete();
        }
    }

    public void deleteAll() {
        deleteStorage();
        createStorage();
    }

    // autre methodes hors acces aux donnees pour la gestion.

    private long nextAvailableId(){
        synchronized (classe) {
            long max = 0;
            for (Produit a : getAll()){
                if (a.getId() > max) max = a.getId();
            }
            return max+1;
        }
    }

    public void deleteStorage(){
        File base = context.getFilesDir();
        deleteFolder(base);
    }

    public void createStorage(){
        File base = context.getFilesDir();
        base.mkdirs();
    }

    private static void deleteFolder(File folder) {
        try{File[] files = folder.listFiles();
            if(files!=null) {
                for(File f: files) {
                    if(f.isDirectory())
                        deleteFolder(f);
                    else
                        f.delete();
                }
            }
            folder.delete();
        }catch(Exception e){}
    }

}
