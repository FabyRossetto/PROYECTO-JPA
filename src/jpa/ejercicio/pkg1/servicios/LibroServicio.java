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
import jpa.ejercicio.pkg1.Entidades.Editorial;
import jpa.ejercicio.pkg1.Entidades.Libro;

/**
 *
 * @author Fabi
 */
public class LibroServicio {

    EntityManagerFactory EMF = Persistence.createEntityManagerFactory("JPA_ejercicio_1PU");
    EntityManager em = EMF.createEntityManager();

    public LibroServicio() {
    }

    public void crearLibro() throws Exception {

        Scanner Leer = new Scanner(System.in, "ISO-8859-1").useDelimiter("\n");
        try {

            Libro esta = buscarLibroPorIsbn();
            if (esta != null) {
                System.out.println("El libro ya esta registrado");
            } else {
                Libro libro = new Libro();
                AutorServicio a = new AutorServicio();
                EditorialServicio e = new EditorialServicio();
                System.out.println("INGRESE EL isbn DEL LIBRO: ");
                long isbn = Leer.nextLong();
                System.out.println("INGRESE TITULO: ");
                String titulo = Leer.next();

                System.out.println("Ingrese el anio del libro");
                int anio = Leer.nextInt();

                Autor autor = a.crearAutor();
                Editorial edi = e.crearEditorial();

                // VALIDACIONES
                if (Long.toString(isbn) == null || Long.toString(isbn).trim().isEmpty()) {
                    throw new Exception("Debe indicar un isbn valido");
                }
                if (titulo == null || titulo.trim().isEmpty()) {
                    throw new Exception("El titulo es invalido");
                }
                if (Integer.toString(anio) == null || Integer.toString(anio).trim().isEmpty()) {
                    throw new Exception("Debe indicar un año valido");
                }

                // SETEO LOS VALORES
                libro.setIsbn(isbn);
                libro.setTitulo(titulo);
                libro.setAnio(anio);
                libro.setAlta(true);
                libro.setAutor(autor);
                libro.setEditorial(edi);

                //los guardo
                em.getTransaction().begin();
                em.persist(libro);
                em.getTransaction().commit();
                System.out.println("Felicitaciones!su libro fue cargado con exito!");
            }
        } catch (Exception e) {
            System.out.println("el libro no se pudo guardar");
        }
    }

    public void ModificarLibro() throws Exception {
        Scanner Leer = new Scanner(System.in).useDelimiter("\n");
        try {

            Libro libro = buscarLibroPorIsbn();
            System.out.println("Quiere modificar el nombre del Libro");
            String respuesta1 = Leer.next();

            if (respuesta1.equalsIgnoreCase("si")) {
                System.out.println("Indique el nombre por el cual quiere modificar el libro guardado");
                String tituloNuevo = Leer.next();
                if (tituloNuevo == null || tituloNuevo.trim().isEmpty()) {
                    throw new Exception("El titulo es invalido");
                }
                libro.setTitulo(tituloNuevo);
            }

            System.out.println("Quiere modificar el autor?");
            String respuesta = Leer.next();

            if (respuesta.equalsIgnoreCase("si")) {
                AutorServicio a = new AutorServicio();
                Autor autor = a.ModificarAutor();
                libro.setAutor(autor);

            }
            System.out.println("Quiere modificar la editorial?");
            String respuesta2 = Leer.next();

            if (respuesta2.equalsIgnoreCase("si")) {
                EditorialServicio e = new EditorialServicio();
                Editorial edi = e.ModificarEditorial();
                libro.setEditorial(edi);

            }

            em.getTransaction().begin();
            em.merge(libro);
            em.getTransaction().commit();
            System.out.println("Felicitaciones!su libro ha sido modificado ");
        } catch (Exception e) {
            System.out.println("No se pudo modificar el libro");
        }
    }
//    //metodo para eliminar entidades
//

    public void borrarLibro() throws Exception {
        Scanner Leer = new Scanner(System.in);
        try {
            Libro libro = buscarLibroPorIsbn();

            em.getTransaction().begin();
            em.remove(libro);
            em.getTransaction().commit();
            System.out.println("El libro ha sido borrado con exito!");
        } catch (Exception e) {
            System.out.println("El libro no se ha podido borrar");
        }
    }

    public Libro buscarLibroPorIsbn() throws Exception {
        Scanner Leer = new Scanner(System.in);
        try {
            System.out.println("Indique el isbn del libro ");
            long isbn = Leer.nextLong();
            // VALIDACIONES
            if (Long.toString(isbn) == null | Long.toString(isbn).trim().isEmpty()) {
                throw new Exception("Debe indicar un isbn valido");
            }

            Libro libro = em.find(Libro.class, isbn);

            System.out.println("El libro es " + libro.getTitulo());

            return libro;
        } catch (Exception e) {
            System.out.println("el libro indicado no se encuentra registrado,puede cargarlo");
            return null;
        }

    }

    public Libro buscarLibroPorTitulo() throws Exception {
        Scanner Leer = new Scanner(System.in);
        try {
            System.out.println("Indique el titulo del libro que desea buscar");
            String titulo = Leer.nextLine();
            // VALIDACIONES
            if (titulo == null | titulo.trim().isEmpty()) {
                throw new Exception("Debe indicar un titulo valido");
            }

            Query q = em.createQuery("SELECT b FROM Libro b WHERE b.titulo LIKE :n").setParameter("n", titulo);

            System.out.println(" " + (Libro) q.getSingleResult());
            return (Libro) q.getSingleResult();
        } catch (Exception e) {
            System.out.println("el libro indicado no se ha podido encontrar");
            return null;
        }
    }

    public void buscarLibroPorAutor() throws Exception {
        try {
            AutorServicio a = new AutorServicio();//esta mal. Hacerlo con una query
            a.buscarAutorPorId();
            //select a from libro a where a.autor.nombre like:="..."
        } catch (Exception e) {
            System.out.println("No se ha podido encontrar el libro conforme ese autor");
        }
    }

    public void buscarLibroPorEditorial() throws Exception {
        try {
            EditorialServicio e = new EditorialServicio();
            e.buscarEditorialPorId();

        } catch (Exception e) {
            System.out.println("No se ha podido encontrar el libro conforme a esa editorial");
        }
    }
}
