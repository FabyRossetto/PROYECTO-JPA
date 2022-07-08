/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package jpa.ejercicio.pkg1.servicios;


import java.util.Scanner;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.Query;
import jpa.ejercicio.pkg1.Entidades.Autor;



/**
 *El autor depende de libro,porque la relacion no es bidereccional,no puedo modificar solo el autor si no accedo al libro primero.
 *
 */
public class AutorServicio {

    EntityManagerFactory EMF = Persistence.createEntityManagerFactory("JPA_ejercicio_1PU");
    EntityManager em = EMF.createEntityManager();

    public AutorServicio() {
    }

//metodo para persistir la entidad autor./Dar de alta
    public Autor crearAutor() throws Exception {

        Scanner Leer = new Scanner(System.in).useDelimiter("\n");
        Autor esta = buscarAutorPorId();
        if (esta != null) {
            System.out.println("El autor ya esta cargado a la pagina");
            return esta;
        } else {
            try {
                // CREO UNA INSTANCIA DE USUARIO

                Autor autor = new Autor();

                System.out.println("INGRESE EL ID DEL AUTOR: ");
                int id = Leer.nextInt();

                System.out.println("INGRESE NOMBRE DEL AUTOR: ");
                String nombre = Leer.next();

                // VALIDACIONES
                if (nombre == null || nombre.trim().isEmpty()) {
                    throw new Exception("NOMBRE NO VÁLIDO");
                }

                if (Integer.toString(id) == null || Integer.toString(id).trim().isEmpty()) {
                    throw new Exception("Debe indicar un ID valido");
                }

                // SETEO LOS VALORES
                autor.setId(id);
                autor.setNombre(nombre);
                autor.setAlta(true);

                //los guardo
                em.getTransaction().begin();//comienzo la transaccion
                em.persist(autor);//los guardo
                em.getTransaction().commit();//termino la transaccion

                System.out.println("su autor se ha creado correctamente");
                return autor;

            } catch (Exception e) {
                System.out.println("el autor no se pudo guardar");
                return null;
            }
        }
    }
//metodo para editar entidades

    public Autor ModificarAutor() throws Exception {
        Scanner Leer = new Scanner(System.in).useDelimiter("\n");
        Autor autor = buscarAutorPorId();
        if (autor != null) {

            System.out.println("Indique el nombre por el cual quiere modificar el autor guardado");
            String nombreNuevo = Leer.next();

            // VALIDACIONES
            if (nombreNuevo == null | nombreNuevo.trim().isEmpty()) {
                throw new Exception("NOMBRE NO VÁLIDO");
            }

            // SETEO LOS VALORES
            autor.setNombre(nombreNuevo);

            em.getTransaction().begin();
            em.merge(autor);
            em.getTransaction().commit();
            System.out.println("Felicitaciones,su autor ha sido modificado");

            return autor;

        } else {
            System.out.println("No se pudo modificar el autor");
            return null;
        }
    }

    //metodo para eliminar entidades
    public void borrarAutor() throws Exception {
        Scanner Leer = new Scanner(System.in);
        try {
            Autor autor = buscarAutorPorId();

            em.getTransaction().begin();
            em.remove(autor);
            em.getTransaction().commit();
            System.out.println("Felicitaciones,su autor ha sido borrado");
        } catch (Exception e) {
            System.out.println("El autor no se ha podido borrar");
        }
    }

    public Autor buscarAutorPorNombre() {
        Scanner Leer = new Scanner(System.in);

        System.out.println("Indique el nombre del autor que desea buscar");
        String nombreAutor = Leer.nextLine();

        Query q = em.createQuery("SELECT b FROM Autor b WHERE b.nombre LIKE :n").setParameter("n", nombreAutor);

        System.out.println(" " + (Autor) q.getSingleResult());
        return (Autor) q.getSingleResult();

    }

    public Autor buscarAutorPorId() throws Exception {
        Scanner Leer = new Scanner(System.in);
        try {
            System.out.println("Indique el id del autor ");
            int id = Leer.nextInt();
            // VALIDACIONES
            if (Integer.toString(id) == null | Integer.toString(id).trim().isEmpty()) {
                throw new Exception("Debe indicar un ID valido");
            }

            Autor autor = em.find(Autor.class, id);//al find se le pasa el tipo de objeto y el valor de la llave primaria. Trae solo un objeto y completo

            System.out.println("El autor es " + autor.getNombre());

            return autor;
        } catch (Exception e) {
            System.out.println("el autor indicado no esta cargado en la base de datos,usted puede ingresarlo");
            return null;
            
            
        }
    }
}
