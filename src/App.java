import java.util.*;

public class App {

    public static void main(String[] args) {

        int numDiscos = 8;

        Torre torre1 = new Torre();
        Torre torre2 = new Torre();
        Torre torre3 = new Torre();

        // Agregar discos
        for (int i = numDiscos; i >= 1; i--) {
            torre1.agregarDisco(new Nodo(i, 1));
        }

        Estado estadoInicial = new Estado(
            torre1,
            torre2,
            torre3
        );

        System.out.println("==================================");
        System.out.println("     TORRES DE HANOI - BFS");
        System.out.println("==================================");

        System.out.println(
            "Número de discos: " +
            numDiscos
        );

        List<String> solucion =
            resolverHanoi(
                estadoInicial,
                numDiscos
            );

        if (solucion != null) {

            System.out.println(
                "\n=================================="
            );

            System.out.println(
                "         SOLUCIÓN FINAL"
            );

            System.out.println(
                "=================================="
            );

            int paso = 0;

            for (String movimiento : solucion) {

                System.out.println(
                    "Paso " +
                    paso +
                    ": " +
                    movimiento
                );

                paso++;
            }

        } else {

            System.out.println(
                "No se encontró solución"
            );
        }
    }

    // BFS
    public static List<String> resolverHanoi(
        Estado estadoInicial,
        int numDiscos
    ) {

        Queue<Estado> cola =
            new LinkedList<>();

        Set<String> visitados =
            new HashSet<>();

        cola.add(estadoInicial);

        visitados.add(
            estadoInicial.generarclave()
        );

        Estado estadoFinal = null;

        int nivel = 0;

        int nodosExplorados = 0;

        long inicio =
            System.currentTimeMillis();

        while (
            !cola.isEmpty() &&
            estadoFinal == null
        ) {

            int tamañoNivel =
                cola.size();

            System.out.println(
                "\n=================================="
            );

            System.out.println(
                "NIVEL BFS: " + nivel
            );

            System.out.println(
                "Nodos en este nivel: " +
                tamañoNivel
            );

            System.out.println(
                "=================================="
            );

            for (
                int i = 0;
                i < tamañoNivel;
                i++
            ) {

                Estado actual =
                    cola.poll();

                nodosExplorados++;

                System.out.println(
                    "\nExplorando nodo:"
                );

                System.out.println(
                    actual.generarclave()
                );

                if (
                    actual.movimiento != null
                ) {

                    System.out.println(
                        "Movimiento: " +
                        actual.movimiento
                    );
                }

                // Verificar meta
                if (
                    esFinal(
                        actual,
                        numDiscos
                    )
                ) {

                    estadoFinal = actual;

                    break;
                }

                // Generar hijos
                List<Estado> siguientes =
                    generarEstadosSiguientes(
                        actual
                    );

                for (
                    Estado siguiente :
                    siguientes
                ) {

                    String clave =
                        siguiente
                        .generarclave();

                    if (
                        !visitados
                        .contains(clave)
                    ) {

                        visitados.add(
                            clave
                        );

                        cola.add(
                            siguiente
                        );

                        System.out.println(
                            "   └── " +
                            siguiente.movimiento
                        );
                    }
                }
            }

            nivel++;
        }

        long fin =
            System.currentTimeMillis();

        System.out.println(
            "\n=================================="
        );

        System.out.println(
            "        ESTADÍSTICAS BFS"
        );

        System.out.println(
            "=================================="
        );

        System.out.println(
            "Nodos explorados: " +
            nodosExplorados
        );

        System.out.println(
            "Niveles recorridos: " +
            nivel
        );

        System.out.println(
            "Tiempo de ejecución: " +
            (fin - inicio) +
            " ms"
        );

        if (estadoFinal != null) {

            return reconstruirCamino(
                estadoFinal
            );
        }

        return null;
    }

    // Estado final
    private static boolean esFinal(
        Estado estado,
        int numDiscos
    ) {

        int discos1 =
            contarDiscos(
                estado.torres[0]
            );

        int discos2 =
            contarDiscos(
                estado.torres[1]
            );

        int discos3 =
            contarDiscos(
                estado.torres[2]
            );

        return
            discos1 == 0 &&
            discos2 == 0 &&
            discos3 == numDiscos;
    }

    // Contar discos
    private static int contarDiscos(
        Torre torre
    ) {

        int contador = 0;

        Nodo actual =
            torre.getTope();

        while (actual != null) {

            contador++;

            actual =
                actual.getSiguiente();
        }

        return contador;
    }

    // Generar hijos
    private static List<Estado>
    generarEstadosSiguientes(
        Estado estadoActual
    ) {

        List<Estado> siguientes =
            new ArrayList<>();

        for (
            int origen = 0;
            origen < 3;
            origen++
        ) {

            for (
                int destino = 0;
                destino < 3;
                destino++
            ) {

                if (origen != destino) {

                    Estado nuevoEstado =
                        moverDisco(
                            estadoActual,
                            origen,
                            destino
                        );

                    if (
                        nuevoEstado != null
                    ) {

                        siguientes.add(
                            nuevoEstado
                        );
                    }
                }
            }
        }

        return siguientes;
    }

    // Mover disco
    private static Estado moverDisco(
        Estado estadoActual,
        int origen,
        int destino
    ) {

        Torre t1 =
            estadoActual
            .torres[0]
            .clonar();

        Torre t2 =
            estadoActual
            .torres[1]
            .clonar();

        Torre t3 =
            estadoActual
            .torres[2]
            .clonar();

        Torre[] torresClonadas = {
            t1,
            t2,
            t3
        };

        Nodo disco =
            torresClonadas[origen]
            .quitarDisco();

        if (disco == null) {
            return null;
        }

        Nodo topeDestino =
            torresClonadas[destino]
            .getTope();

        // Regla Hanoi
        if (
            topeDestino != null &&
            disco.getValor() >
            topeDestino.getValor()
        ) {

            torresClonadas[origen]
                .agregarDisco(disco);

            return null;
        }

        torresClonadas[destino]
            .agregarDisco(disco);

        Estado nuevoEstado =
            new Estado(
                torresClonadas[0],
                torresClonadas[1],
                torresClonadas[2]
            );

        nuevoEstado.padre =
            estadoActual;

        nuevoEstado.movimiento =
            "Mover disco " +
            disco.getValor() +
            " de Torre " +
            (origen + 1) +
            " a Torre " +
            (destino + 1);

        return nuevoEstado;
    }

    // Reconstruir camino
    private static List<String>
    reconstruirCamino(
        Estado estadoFinal
    ) {

        List<String> camino =
            new ArrayList<>();

        Estado actual =
            estadoFinal;

        while (actual != null) {

            if (
                actual.movimiento != null
            ) {

                camino.add(
                    0,
                    actual.movimiento
                );
            }

            actual =
                actual.padre;
        }

        camino.add(
            0,
            "Estado Inicial"
        );

        return camino;
    }
}