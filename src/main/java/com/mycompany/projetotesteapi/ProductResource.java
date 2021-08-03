/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.projetotesteapi;

import jakarta.ws.rs.Path;
import jakarta.ws.rs.DELETE;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.PUT;
import jakarta.ws.rs.PathParam;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import java.net.URI;
import java.net.URISyntaxException;

import java.util.List;

/**
 *
 * @author Joao
 */

@Path("/products")
public class ProductResource {
    
    private ProductDAO dao = ProductDAO.getInstance(); // Singleton
    
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public List<Product> list() {
        List lista = dao.listAll();
        return dao.listAll();
    }
    
    // CREATE
    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    public Response add(Product prod) throws URISyntaxException {
        int newProdId = dao.add(prod);
        URI uri = new URI("/products/" + newProdId);
        return Response.created(uri).build();
    }
    
    // READ
    @GET
    @Path("{id}")
    public Response get(@PathParam("id") int id) {
        Product prod = dao.get(id);
        if(prod != null) {
            return Response.ok(prod, MediaType.APPLICATION_JSON).build();
        }
        return Response.status(Response.Status.NOT_FOUND).build();
    }
    
    // UPDATE
    @PUT
    @Consumes(MediaType.APPLICATION_JSON)
    @Path("{id}")
    public Response update(@PathParam("id") int id, Product prod) {
        prod.setId(id);
        if(dao.update(prod)) {
            return Response.ok().build();
        }
        return Response.notModified().build();
    }
    
    // DELETE
    @DELETE
    @Path("{id}")
    public Response delete(@PathParam("id") int id) {
        if(dao.delete(id)) {
            return Response.ok().build();
        }
        return Response.notModified().build();
    }
    
}
