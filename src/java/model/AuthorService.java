/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 *
 * @author Matthew_2
 */
public class AuthorService {
    private List<Author> authorList = new ArrayList<>();
   
    Author a1 = new Author(0,"Bob",new Date(0163,4,1));  
    Author a2 = new Author(1,"Bill",new Date(0161,4,2));
    Author a3 = new Author(2,"Jane",new Date(0162,4,5));
    
    /**
     * method getAllAuthor
     * @return the author list
     */
    public List<Author> getAllAuthor(){
        
        authorList.add(a1);
        authorList.add(a2);
        authorList.add(a3);
        
        return authorList;
    }
}
