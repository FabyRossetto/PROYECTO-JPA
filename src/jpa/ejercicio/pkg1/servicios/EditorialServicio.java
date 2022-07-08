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


import jpa.ejercicio.pkg1.Entidades.Editorial;

/**
 *
 * @author Fabi
 */
public class EditorialServicio {

    EntityManagerFactory EMF = Persistence.createEntityManagerFactory("JPA_ejercicio_1PU");
    EntityManager em = EMF.createEntityManager();

    public EditorialServicio() {
    }

//metodo para persistir la entidad editorial./Dar de alta
    public Editorial crearEditorial() throws Exception {

        Scanner Leer = new Scanner(System.in).useDelimiter("\n");
        Editorial esta = buscarEditorialPorId();
        if (esta != null) {
            System.out.println("La editorial ya esta cargada en la pagina");
            return esta;
        } else {
            try {

                Editorial e = new Editorial();

                System.out.println("INGRESE EL ID DE LA EDITORIAL: ");
                int id = Leer.nextInt();

                System.out.println("INGRESE NOMBRE DE LA EDITORIAL: ");
                String nombre = Leer.next();

                // VALIDACIONES
                if (nombre == null || nombre.trim().isEmpty()) {
                    throw new Exception("NOMBRE NO VÁLIDO");
                }

                if (Integer.toString(id) == null || Integer.toString(id).trim().isEmpty()) {
                    throw new Exception("Debe indicar un ID valido");
                }

                // SETEO LOS VALORES
                e.setId(id);
                e.setNombre(nombre);
                e.setAlta(true);

                //los guardo
                em.getTransaction().begin();//comienzo la transaccion
                em.persist(e);//los guardo
                em.getTransaction().commit();//termino la transaccion
                return e;

            } catch (Exception e) {
                System.out.println("la editorial no se pudo guardar");
                return null;
            }
        }

    }
//metodo para editar entidades

    public Editorial ModificarEditorial() throws Exception {
        Scanner Leer = new Scanner(System.in).useDelimiter("\n");
        try {

            Editorial e = buscarEditorialPorId();
            //no modifico el id,ni el alta,solo el nombre
            if (e == null) {
                Editorial edi = crearEditorial();
                return edi;
            } else {

                System.out.println("Indique el nombre por el cual quiere modificar la editorial guardada");
                String nombreNuevo = Leer.next();
                //no se le puede modificar el id por ser llave primaria
               
                // VALIDACIONES
                if (nombreNuevo == null | nombreNuevo.trim().isEmpty()) {
                    throw new Exception("NOMBRE NO VÁLIDO");
                }

                // SETEO LOS VALORES
                e.setNombre(nombreNuevo);

                em.getTransaction().begin();//abro transaccion
                em.merge(e);//modifico el nombre
                em.getTransaction().commit();//cierro transaccion
                System.out.println("La editorial se modifico correctamente");
                return e;
            }
        } catch (Exception e) {
            System.out.println("No se pudo modificar la editorial");
            return null;
        }
    }
//    //metodo para eliminar entidades
//

    public void borrarEditorial() throws Exception {
        Scanner Leer = new Scanner(System.in);
        try {
            Editorial edi = buscarEditorialPorId();

            em.getTransaction().begin();
            em.remove(edi);
            em.getTransaction().commit();
            System.out.println("La edorial ha sido borrada");
        } catch (Exception e) {
            System.out.println("La editorial no se ha podido borrar");
        }
    }

    public Editorial buscarEditorialPorId() throws Exception {
        Scanner Leer = new Scanner(System.in);
        try {
            System.out.println("Indique el id de la editorial");
            int id = Leer.nextInt();
            // VALIDACIONES
            if (Integer.toString(id) == null | Integer.toString(id).trim().isEmpty()) {
                throw new Exception("Debe indicar un ID valido");
            }

            Editorial edi = em.find(Editorial.class, id);//metodo para buscar

            System.out.println("La editorial es" + edi.getNombre());

            return edi;
        } catch (Exception e) {
            System.out.println("La editorial indicada no esta cargada en la pagina,pero puede cargarla");
            return null;
        }
    }

    public Editorial buscarEditorialPorNombre() throws Exception {
        Scanner Leer = new Scanner(System.in);
        try {
            System.out.println("Indique el nombre de la editorial que desea buscar");
            String ne = Leer.nextLine();
            // VALIDACIONES
            if (ne == null | ne.trim().isEmpty()) {
                throw new Exception("Debe indicar un nombre valido");
            }
            Query q = em.createQuery("SELECT e FROM Editorial e WHERE e.nombre LIKE :n").setParameter("n", ne);

            System.out.println(" " + (Editorial) q.getSingleResult());
            return (Editorial) q.getSingleResult();

        } catch (Exception e) {
            System.out.println("la editorial no se ha podido encontrar");
            return null;
        }
    }
}
