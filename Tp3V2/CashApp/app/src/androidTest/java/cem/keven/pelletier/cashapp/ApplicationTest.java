package cem.keven.pelletier.cashapp;

import android.test.AndroidTestCase;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;


public class ApplicationTest extends AndroidTestCase {

    CRUD<Produit> repo;

    @Override
    protected void setUp() throws Exception {
        super.setUp();
        repo = RepositoryProduit.get(getContext());
        repo.deleteAll();

    }

    @Override
    protected void tearDown() throws Exception {
        super.tearDown();
        repo.deleteAll();
        repo = null;
    }

    public void testSaveAndGetAll(){
        Produit p = new Produit(1234434,"Produit ",10.0);
        repo.save(p);
        assertEquals(repo.getAll().size(), 1);
    }

    public void testSaveManyAndGetAll(){
        List<Produit> prods = new ArrayList<Produit>();
        int size = 22;
        for (int i = 0 ; i < size ; i++){
            Produit p = new Produit(123443,"Produit " + i,i*10);
            prods.add(p);
        }
        repo.saveMany(prods);
        assertEquals(size, repo.getAll().size());
    }

    public void testGetById(){
        Produit p = new Produit(1234436,"Produit ",10.0);
        long tested = repo.save(p);
        Produit recov = repo.getById(tested);
        assertEquals(recov.getPrix(), 10.0);
    }

    public void testDeleteOne(){
        Produit p = new Produit(1234436,"Produit ",10);
        repo.save(p);
        assertEquals(1, repo.getAll().size());
        repo.deleteOne(p);
        assertEquals(0, repo.getAll().size());
    }

    public void testDeleteOneById(){
        Produit p = new Produit(1234436,"Produit ",10);
        repo.save(p);
        assertEquals(1, repo.getAll().size());
        repo.deleteOne(p.getId());
        Log.i("TestsCRUD", repo.getAll().toString());
        assertEquals(0, repo.getAll().size());
    }

    public void testDeleteAll(){
        List<Produit> prods = new ArrayList<Produit>();
        int size = 22;
        for (int i = 0 ; i < size ; i++){
            Produit p = new Produit(123443,"Produit" + i, i*10);
            prods.add(p);
        }
        repo.saveMany(prods);
        assertEquals(size, repo.getAll().size());
        repo.deleteAll();
        assertEquals(0, repo.getAll().size());
    }

}