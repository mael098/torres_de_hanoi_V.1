# Reporte – Torres de Hanoi V.1 & V.2

**Nombre:** Iván Asdrúbal Villegas Espinosa  
**Carrera:** Ingeniería de Sistemas Computacionales  
**Materia:** Tópicos Selectos de Inteligencia Artificial  
**Asignatura:** Programación / Algoritmos y Estructuras de Datos  
**Lenguaje:** Java SE  
**Versiones:** V.1 – Consola | V.2 – Swing GUI  
**Fecha:** 16 de marzo de 2026  
**Repositorio:** https://github.com/mael098/torres_de_hanoi_V.1

---

## 1. Introducción

Las **Torres de Hanoi** es uno de los problemas clásicos de la informática y las matemáticas recreativas, propuesto por el matemático francés **Édouard Lucas** en 1883. A pesar de su sencilla descripción, ilustra de forma excepcional el poder y la elegancia de la **recursividad** como técnica de resolución de problemas.

El problema consiste en mover una pila de **n** discos de diferente tamaño desde una torre de origen hasta una torre de destino, utilizando una torre auxiliar y respetando únicamente dos reglas: mover solo un disco a la vez y nunca colocar un disco más grande sobre uno más pequeño.

> **Este proyecto implementa la solución en dos versiones:**
> - **V.1:** línea de comandos (entrada por teclado).
> - **V.2:** interfaz gráfica Swing con animación paso a paso.

---

## 2. Objetivos

### Objetivo general

Implementar el algoritmo recursivo de las Torres de Hanoi en Java, demostrando su correctitud y eficiencia, y presentarlo en dos versiones funcionales: consola e interfaz gráfica.

### Objetivos específicos

- Comprender y aplicar el paradigma de programación recursiva.
- Analizar la complejidad temporal y espacial del algoritmo.
- Desarrollar una interfaz gráfica atractiva con Java Swing (V.2) que anime la solución.
- Validar la solución con distintas cantidades de discos y verificar el contador de movimientos.

---

## 3. Descripción del problema

### 3.1 Reglas

1. Solo se puede mover **un disco a la vez**.
2. Solo se puede mover el disco que esté en la **cima** de una torre.
3. Ningún disco puede colocarse sobre un disco **más pequeño**.

### 3.2 Torres

El problema usa tres torres nombradas **A** (origen), **B** (auxiliar) y **C** (destino).

```
Estado inicial (3 discos):
  |          |          |
 [1]         |          |
 [2]         |          |
[3  ]        |          |
──────      ──────     ──────
  A            B          C

Estado final:
  |          |          |
  |          |         [1]
  |          |         [2]
  |          |        [3  ]
──────      ──────     ──────
  A            B          C
```

---

## 4. Algoritmo recursivo

### 4.1 Estrategia de división

La solución recursiva divide el problema en **tres pasos** que se aplican para cada nivel de recursión:

1. Mover los **n − 1** discos superiores de **origen** a **auxiliar**, usando **destino** como apoyo.
2. Mover el disco más grande (disco **n**) de **origen** a **destino**.
3. Mover los **n − 1** discos de **auxiliar** a **destino**, usando **origen** como apoyo.

### 4.2 Pseudocódigo

```text
procedimiento hanoi(n, origen, destino, auxiliar):
    si n == 1:
        mover disco 1 de origen → destino
        retornar
    hanoi(n-1, origen, auxiliar, destino)   // paso 1
    mover disco n de origen → destino       // paso 2
    hanoi(n-1, auxiliar, destino, origen)   // paso 3
```

### 4.3 Traza para n = 3

| # | Disco | Desde | Hacia |
|---|:-----:|:-----:|:-----:|
| 1 | 1     | A     | C     |
| 2 | 2     | A     | B     |
| 3 | 1     | C     | B     |
| 4 | 3     | A     | C     |
| 5 | 1     | B     | A     |
| 6 | 2     | B     | C     |
| 7 | 1     | A     | C     |

Total: **7 movimientos** (= 2³ − 1)

---

## 5. Análisis de complejidad

### 5.1 Número de movimientos

La recurrencia que describe el número de movimientos **T(n)** es:

**T(n) = 2·T(n − 1) + 1**  (T(1) = 1)

La solución cerrada es:

**T(n) = 2ⁿ − 1**

### 5.2 Tabla de crecimiento

| Discos (n) | Movimientos (2ⁿ − 1) | Complejidad |
|------------|----------------------|-------------|
| 1          | 1                    | **O(2ⁿ)**   |
| 2          | 3                    |             |
| 3          | 7                    |             |
| 4          | 15                   |             |
| 5          | 31                   |             |
| 6          | 63                   |             |
| 7          | 127                  |             |
| 8          | 255                  |             |

### 5.3 Complejidad espacial

El algoritmo usa la pila de llamadas del sistema; la profundidad máxima de recursión es **O(n)**.

> **Nota:** la complejidad temporal es **exponencial**. Para n grandes el número de movimientos crece muy rápido (por ejemplo, n = 64 requiere ~1.8 × 10¹⁹ movimientos).

---

## 6. Código fuente V.1 (Consola)

### 6.1 Explicación

- **Scanner** lee el número de discos desde la entrada estándar.
- Se valida que el número sea positivo.
- El método `resolverTorresHanoi()` implementa la recursividad.
- La variable estática `contadorMovimientos` acumula el total de movimientos.

### 6.2 Código

```java
import java.util.Scanner;

public class App {

    private static int contadorMovimientos = 0;

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        System.out.println("=== TORRES DE HANOI V.1 ===");
        System.out.print("Ingrese el numero de discos: ");

        int discos = scanner.nextInt();

        if (discos <= 0) {
            System.out.println("El numero de discos debe ser mayor que 0.");
            scanner.close();
            return;
        }

        resolverTorresHanoi(discos, 'A', 'C', 'B');
        System.out.println("Total de movimientos: " + contadorMovimientos);

        scanner.close();
    }

    private static void resolverTorresHanoi(int n, char origen, char destino, char auxiliar) {
        if (n == 1) {
            contadorMovimientos++;
            System.out.println("Movimiento " + contadorMovimientos + ": disco 1 de " + origen + " a " + destino);
            return;
        }

        resolverTorresHanoi(n - 1, origen, auxiliar, destino);

        contadorMovimientos++;
        System.out.println("Movimiento " + contadorMovimientos + ": disco " + n + " de " + origen + " a " + destino);

        resolverTorresHanoi(n - 1, auxiliar, destino, origen);
    }
}
```

### 6.3 Ejemplo de salida (3 discos)

```
=== TORRES DE HANOI V.1 ===
Ingrese el numero de discos: 3
Movimiento 1: disco 1 de A a C
Movimiento 2: disco 2 de A a B
Movimiento 3: disco 1 de C a B
Movimiento 4: disco 3 de A a C
Movimiento 5: disco 1 de B a A
Movimiento 6: disco 2 de B a C
Movimiento 7: disco 1 de A a C
Total de movimientos: 7
```

---

## 7. Versión 2 – Interfaz Gráfica (Swing)

### 7.1 Tecnología utilizada

Java SE con la biblioteca **Swing** (`javax.swing`) para la interfaz gráfica y `javax.swing.Timer` para la animación paso a paso.

### 7.2 Componentes de la interfaz

- **JFrame:** Ventana principal (980 × 640 px)
- **JSpinner:** Selector de número de discos (3 – 8)
- **JButton "Resolver":** Genera todos los movimientos e inicia la animación
- **JButton "Reiniciar":** Restablece el estado inicial
- **JLabel (estado):** Muestra el progreso (movimiento actual / total)
- **HanoiPanel (JPanel):** Panel personalizado dibuja torres y discos con colores

### 7.3 Características visuales

- Fondo con degradado azul claro.
- Torres dibujadas con `Graphics2D.fillRoundRect()`.
- Cada disco tiene un color único tomado de una paleta de 8 colores.
- Anti-aliasing activado para bordes suaves.
- Animación controlada por `Timer` (500 ms por paso).
- Diálogo final con el resumen de movimientos.

### 7.4 Diagrama de clases simplificado

```
┌─────────────┐           ┌────────────────────┐
│    App      │ crea ───▶ │   HanoiFrame       │
│  (main)     │           │ ─────────────────  │
└─────────────┘           │ + hanoiPanel       │
                          │ + discosSpinner    │
                          │ + resolverBtn      │
                          │ + reiniciarBtn     │
                          │ + animacionTimer   │
                          │ + movimientos[]    │
                          │ ─────────────────  │
                          │ + iniciarRes()     │
                          │ + reiniciarJuego() │
                          └────────┬───────────┘
                                   │ contiene
                          ┌────────▼───────────┐
                          │   HanoiPanel       │
                          │ ─────────────────  │
                          │ + torres[3]        │
                          │ + numeroDiscos     │
                          │ ─────────────────  │
                          │ + reiniciar(n)     │
                          │ + moverDisco(o,d)  │
                          │ + paintComponent() │
                          └────────────────────┘
```

> **Nota:** La V.2 mantiene exactamente el mismo algoritmo recursivo de la V.1; solo añade la capa gráfica para visualizar cada movimiento de forma animada.

---

## 8. Comparativa V.1 vs V.2

| Característica | V.1 – Consola | V.2 – Interfaz Gráfica |
|----------------|---------------|------------------------|
| Entrada de usuario | Teclado (Scanner) | JSpinner con botones |
| Salida | Texto en consola | Animación gráfica en ventana |
| Visualización de torres | No | Sí (discos de colores) |
| Animación paso a paso | No | Sí (Timer 500 ms) |
| Algoritmo recursivo | Sí | Sí (mismo algoritmo) |
| Contador de movimientos | Sí (texto) | Sí (label dinámico) |
| Máx. discos recomendados | Ilimitado (imprime en texto) | 8 (visual, fluido) |
| Líneas de código | ~40 | ~260 |
| Dependencias externas | Ninguna | Java Swing (incluido en JDK) |

---

## 9. Conclusiones

- El problema de las Torres de Hanoi es un ejemplo perfecto de cómo la **recursividad** permite resolver problemas complejos con código conciso y elegante.
- La solución tiene complejidad **exponencial O(2ⁿ)**, lo que la hace inviable para un número grande de discos, pero óptima en términos de número mínimo de movimientos.
- La separación entre la lógica (algoritmo) y la presentación (interfaz gráfica) demuestra la importancia del principio de **separación de responsabilidades**.
- La interfaz gráfica en Swing mejora significativamente la comprensión del algoritmo al visualizar cada movimiento de disco en tiempo real.

---

## 10. Instrucciones de compilación y ejecución

```bash
# Compilar (desde la raíz del proyecto)
javac -d bin src/App.java

# Ejecutar V.1 (consola)
java -cp bin App

# Ejecutar V.2 (GUI — solo requiere un entorno gráfico)
java -cp bin App
```

> Requiere **Java SE 11+**. Para la V.2 es necesario un entorno de escritorio (X11 / Windows / macOS).

---

## 11. Referencias

- Lucas, É. (1883). *Récréations Mathématiques*. Gauthier-Villars, Paris.
- Cormen, T. H., Leiserson, C. E., Rivest, R. L., & Stein, C. (2009). *Introduction to Algorithms* (3ª ed.). MIT Press.
- Oracle. (2023). *Java Platform SE Documentation*. https://docs.oracle.com/javase/
- Oracle. (2023). *Creating a GUI With Swing*. Oracle Java Tutorials.
