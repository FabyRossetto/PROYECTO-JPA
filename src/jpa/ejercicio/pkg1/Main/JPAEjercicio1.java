/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package jpa.ejercicio.pkg1.Main;

import java.util.InputMismatchException;
import java.util.Scanner;

import jpa.ejercicio.pkg1.servicios.AutorServicio;
import jpa.ejercicio.pkg1.servicios.EditorialServicio;
import jpa.ejercicio.pkg1.servicios.LibroServicio;

/**
 *
 * @author Fabi
 */
public class JPAEjercicio1 {

    /**
     * @param args the command line arguments
     * @throws java.lang.Exception
     */
    public static void main(String[] args) throws Exception {
         Scanner Leer =new Scanner(System.in);
         int opcion = 0;
         AutorServicio autor = new AutorServicio();
         LibroServicio libro= new LibroServicio();
         EditorialServicio editorial=new EditorialServicio();
    
        do{
            try {
                System.out.println("ELIJA UNA OPCIÓN");
                System.out.println("1. CREAR AUTOR");//funciona
                System.out.println("2. MODIFICAR AUTOR");//funciona
                System.out.println("3. ELIMINAR AUTOR");//funciona
                System.out.println("4. BUSCAR AUTOR POR ID");//funciona
                System.out.println("5. BUSCAR AUTOR POR NOMBRE");//funciona!!
                
                System.out.println("6. CREAR EDITORIAL");//funciona
                System.out.println("7. MODIFICAR EDITORIAL");//funciona
                System.out.println("8. ELIMINAR EDITORIAL");//funciona
                System.out.println("9. BUSCAR EDITORIAL POR ID");//funciona
                System.out.println("10. BUSCAR EDITORIAL POR NOMBRE");//funciona
                
                System.out.println("11. CREAR LIBRO");//funciona
                System.out.println("12. MODIFICAR LIBRO");//funciona
                System.out.println("13. ELIMINAR LIBRO");//funciona
                System.out.println("14. BUSCAR LIBRO POR ISBN");//funciona
                System.out.println("15. BUSCAR LIBRO POR TITULO");//funciona
                System.out.println("16. BUSCAR LIBRO POR AUTOR");//funciona
                System.out.println("17. BUSCAR LIBRO POR EDITORIAL");//funciona
                System.out.println("0. SALIR");//funciona

                opcion = Leer.nextInt();
                

                switch (opcion) {
                    case 1:
                        autor.crearAutor();
                        break;
                    case 2:
                        autor.ModificarAutor();
                        break;
                    case 3:
                       autor.borrarAutor();
                        break;
                    case 4:
                        autor.buscarAutorPorId();
                        break;
                    case 5:
                        autor.buscarAutorPorNombre();
                        break;
                    case 6:
                        editorial.crearEditorial();
                        break;
                    case 7:
                        editorial.ModificarEditorial();
                        break;
                    case 8:
                       editorial.borrarEditorial();
                        break;
                    case 9:
                        editorial.buscarEditorialPorId();
                        break;
                    case 10:
                        editorial.buscarEditorialPorNombre();
                        break;
                        
                    case 11:
                        libro.crearLibro();
                   break;
                    case 12:
                        libro.ModificarLibro();
                        break;
                    case 13:
                        libro.borrarLibro();
                        break;
                    case 14:
                        libro.buscarLibroPorIsbn();
                        break;
                    case 15:
                        libro.buscarLibroPorTitulo();
                        break;
                    case 16:
                        libro.buscarLibroPorAutor();
                        break;
                     case 17:
                        libro.buscarLibroPorEditorial();
                        break;
                    case 0:
                        System.out.println("*** SESION FINZALIZADA ***");
                        break;
                    default:
                        System.out.println("LA OPCIÓN INGRESADA NO ES VÁLIDA");
                }
            } catch (InputMismatchException e) {
                System.out.println("NO SE ADMITEN CARACTERES");
                Leer.next();
            } catch (Exception e) {
                
                System.out.println(e.getMessage());
            }
        } while (opcion != 0);
    }
}

    