package cem.keven.pelletier.cashapp;

import android.content.Context;

import com.google.gson.Gson;

import org.apache.commons.io.FileUtils;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by kinpell on 27/04/2015.
 */
public class RepositoryAchats implements CRUD<Commande> {

    Gson gson = new Gson();

    Class<Commande> classe = Commande.class;

    Context context;

    private static RepositoryAchats one;

    private RepositoryAchats(Context c){
        this.context = c;
        this.createStorage();
    }

    public static synchronized RepositoryAchats get(Context context){
        if (one == null) one = new RepositoryAchats(context);
        return one;
    }

    public static synchronized void release() {
        one = null;
    }

    @Override
    public long save(Commande a) {
        synchronized (classe) {
            // set the id
            if (a.getId() == null) a.setId(this.nextAvailableId());
            //
            String serialise = gson.toJson(a);
            File base = context.getFilesDir();
            try {
                FileUtils.writeStringToFile(new File(base, a.getId()+".achat"), serialise);
            } catch (IOException e) {
                e.printStackTrace();
            }
            return a.getId();

        }
    }

    @Override
    public void saveMany(Iterable<Commande> list) {
        for (Commande p : list ){
            this.save(p);
        }
    }

    @Override
    public void saveMany(Commande... list) {
        for (Commande p : list ){
            this.save(p);
        }
    }

    @Override
    public Commande getById(Long id) {
        synchronized (classe) {
            String content;
            try {
                File base = context.getFilesDir();
                File f = new File(base,id+".achat");
                if (!f.exists()) return null;
                content = FileUtils.readFileToString(new File(base,id+".achat"));
                Commande a = gson.fromJson(content, classe);
                return a;
            } catch (IOException e) {
                return null;
            }
        }
    }

    @Override
    public List<Commande> getAll() {

        synchronized (classe) {
            List<Commande> res = new ArrayList<Commande>();
            File base = context.getFilesDir();
            for (File f : base.listFiles()) {
                try {
                    //System.out.println("File is "+f.getName());
                    String content = FileUtils.readFileToString(f);
                    Commande a = gson.fromJson(content, classe);
                    res.add(a);
                } catch (Exception e) {
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

    @Override
    public void deleteOne(Commande o) {
        File base = context.getFilesDir();
        File f = new File(base, o.getId()+".achat");
        f.delete();
    }

    @Override
    public void deleteAll() {
        deleteStorage();
        createStorage();
    }

    private long nextAvailableId(){
        synchronized (classe) {
            long max = 0;
            for (Commande a : getAll()){
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
