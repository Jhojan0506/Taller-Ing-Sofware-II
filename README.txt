================================================================
  AutoCar — Patrones de Diseño Creacionales
  Taller Calificable · Curso de Patrones OO
================================================================

----------------------------------------------------------------
ESTRUCTURA DE ARCHIVOS
----------------------------------------------------------------

  Vehiculo.java               Clase abstracta + contrato Prototype
  Auto.java                   Subclase concreta (Prototype)
  Van.java                    Subclase concreta (Prototype)
  CamionLigero.java           Subclase concreta (Prototype)
  RegistroVehiculos.java      Registro de plantillas clonables
  Inventario.java             Arreglo de objetos + operaciones
  ContratoAlquiler.java       Producto final del Builder (inmutable)
  ContratoAlquilerBuilder.java Builder concreto con validaciones
  Director.java               Director con recetas predefinidas
  Main.java                   Demostración completa del sistema

----------------------------------------------------------------
COMPILACIÓN Y EJECUCIÓN
----------------------------------------------------------------

  cd AutoCar_PatronesCreacionales
  javac *.java
  java Main

----------------------------------------------------------------
ESCENARIO 1 — INVENTARIO: PATRÓN PROTOTYPE
----------------------------------------------------------------

PATRÓN ELEGIDO: Prototype
ALTERNATIVA DESCARTADA: Factory Method

--- Por qué NO se eligió Factory Method ---

El Factory Method delega la creación a subclases de una fábrica.
Cada vez que se necesita un vehículo, la fábrica lo construye
desde cero pasando todos los parámetros: modelo, año, autonomía,
número de puertas, etc.

En AutoCar esto genera dos problemas concretos:

1. REPETICIÓN: si se necesitan 20 autos del mismo modelo BYD
   Dolphin 2024, el cliente debe pasar los mismos 5 parámetros
   20 veces. Cualquier error de digitación produce un vehículo
   con datos distintos al resto de la flota.

2. EXTENSIÓN COSTOSA: agregar un nuevo tipo de vehículo con
   Factory Method implica crear una nueva subclase de la fábrica
   y modificar el método de selección (if/switch). Esto viola
   el Principio Abierto/Cerrado.

Además, el Factory Method no da ventaja cuando la lógica de
construcción es simple (solo un constructor con parámetros fijos).
Su fuerza está en delegar a subclases qué objeto crear, no en
reutilizar objetos ya configurados.

--- Por qué SÍ se eligió Prototype ---

En el sistema AutoCar los vehículos comparten casi todos sus
atributos por modelo: autonomía, tipo, año y características
técnicas. Lo único que varía al registrar una unidad en el
inventario es la placa.

Con Prototype se define una plantilla base una sola vez:

  registro.registrar("auto_urbano",
      new Auto("PLANTILLA-A", 320, "BYD Dolphin", 2024, 4));

Y se clona cuantas veces sea necesario, cambiando solo la placa:

  Vehiculo a1 = registro.clonar("auto_urbano", "ABC-123");
  Vehiculo a2 = registro.clonar("auto_urbano", "DEF-456");

Agregar un tipo nuevo (por ejemplo, una moto eléctrica) solo
requiere registrar un prototipo adicional. No se modifica ninguna
fábrica ni se toca código existente.

--- Comparación directa ---

Criterio                      Factory Method        Prototype (elegido)
----------------------------  --------------------  --------------------
Reutiliza objetos existentes  No, recrea desde 0    Sí, clona la plantilla
Código para N unidades/modelo Repite parámetros     Un solo registro
Agregar nuevo tipo            Nueva subclase factory Solo registrar plantilla
Personalizar post-creación    Difícil               Natural (cambiar placa)
Adecuado cuando...            Lógica de creación    Objetos con atributos
                              varía por subtipo     mayormente fijos

--- Gestión del inventario con arreglo de objetos ---

La clase Inventario usa:

  private final Vehiculo[] vehiculos = new Vehiculo[CAPACIDAD_MAX];
  private int cantidad = 0;

Operaciones implementadas:
  agregar(v)           Valida duplicados de placa y capacidad máxima
  buscarPorPlaca(p)    Búsqueda lineal O(n), retorna null si no existe
  ordenarPorAutonomia  Ordenamiento burbuja O(n²), mayor a menor
  mostrar()            Imprime el arreglo en el orden actual

No se usa ArrayList ni ninguna colección dinámica de Java,
cumpliendo el requisito explícito del enunciado.

----------------------------------------------------------------
ESCENARIO 2 — CONTRATOS: PATRÓN BUILDER
----------------------------------------------------------------

PATRÓN ELEGIDO: Builder
ALTERNATIVA DESCARTADA: Abstract Factory

--- Por qué NO se eligió Abstract Factory ---

El Abstract Factory crea familias de objetos relacionados,
garantizando que los productos sean compatibles entre sí.
Por ejemplo, podría garantizar que un contrato de tipo
"Empresarial" siempre use el plan empresarial Y los accesorios
empresariales, sin mezclar componentes de otra familia.

Sin embargo, en AutoCar el Abstract Factory tiene tres debilidades
frente al problema real:

1. NO CONTROLA LA CONSTRUCCIÓN PASO A PASO: el Abstract Factory
   entrega objetos terminados, no permite agregar accesorios
   uno a uno ni validar reglas durante el ensamblaje. Si se
   quiere GPS pero no seguro, se necesitaría una fábrica
   distinta para cada combinación posible.

2. NO INTEGRA REGLAS DE NEGOCIO DURANTE LA CREACIÓN: la regla
   "si duración > 30 días aplicar 10% de descuento" no tiene
   un lugar natural dentro de un Abstract Factory. Quedaría
   dispersa en el código cliente o en una clase externa.

3. EXPLOSIÓN DE FÁBRICAS: con 3 planes y 3 accesorios opcionales
   habría potencialmente 8 combinaciones de fábricas concretas.
   Eso es código que no escala.

El Abstract Factory sería la elección correcta si el problema
fuera garantizar compatibilidad entre familias enteras de objetos
(plan Particular + vehículo Particular + accesorios Particulares
vs. la familia Empresarial), sin necesidad de validaciones
cruzadas ni construcción incremental.

--- Por qué SÍ se eligió Builder ---

Un contrato de alquiler tiene naturaleza mixta:

  Campos OBLIGATORIOS: cliente, vehículo, plan, duración
  Campos OPCIONALES:   GPS, seguro, cargador portátil

El Builder resuelve exactamente esto:

1. CONSTRUCCIÓN PASO A PASO: el cliente añade solo los
   componentes que necesita mediante métodos encadenados.

     new ContratoAlquilerBuilder()
         .setCliente("Laura Pérez")
         .setVehiculo(auto)
         .setPlan("Diario")
         .setDuracionDias(5)
         .addGPS()
         .build();

2. VALIDACIÓN CENTRALIZADA: build() verifica que todos los
   campos obligatorios estén presentes antes de crear el
   objeto. Si falta alguno, lanza IllegalStateException.
   Nunca se entrega un contrato en estado inválido.

3. REGLAS DE NEGOCIO INTEGRADAS: el descuento del 10% para
   duraciones mayores a 30 días se calcula en un único lugar
   dentro de build(), no disperso en el código cliente.

4. INMUTABILIDAD DEL PRODUCTO: ContratoAlquiler no tiene
   setters. Una vez construido, sus datos no pueden alterarse.

--- Comparación directa ---

Criterio                       Abstract Factory      Builder (elegido)
-----------------------------  --------------------  --------------------
Construcción paso a paso       No                    Sí
Campos opcionales variables    Requiere N fábricas   Métodos opcionales
Validación durante creación    No tiene lugar natural Centralizada en build()
Reglas de negocio (descuento)  Dispersa en cliente   Integrada en build()
Producto inmutable             Depende               Garantizado
Cuándo es mejor                Familias compatibles  Objeto complejo con
                               de objetos            partes opcionales

----------------------------------------------------------------
VENTAJAS DE LA SOLUCIÓN
----------------------------------------------------------------

1. Extensibilidad: agregar un nuevo tipo de vehículo solo
   requiere crear la subclase y registrar un prototipo.
   No se modifica código existente (Principio OCP).

2. Consistencia garantizada: build() impide contratos inválidos
   lanzando excepción antes de crear el objeto.

3. API legible: el Builder permite código autodocumentado y
   fácil de auditar.

4. Separación de responsabilidades: cada clase tiene una única
   razón de cambio (Principio SRP).

----------------------------------------------------------------
LIMITACIONES DE LA SOLUCIÓN
----------------------------------------------------------------

1. Inventario fijo: el arreglo de tamaño 100 no escala sin
   refactorización. En producción se usaría ArrayList o BD.

2. Ordenamiento O(n²): burbuja es ineficiente para flotas
   grandes. Se reemplazaría por Arrays.sort() con Comparator.

3. Prototype sin deep copy: si Vehiculo tuviera atributos de
   tipo objeto mutable (List<String>), el clon compartiría
   referencias. Requeriría implementar Cloneable correctamente.

4. Builder no thread-safe: guarda estado mutable entre llamadas.
   En entornos concurrentes, cada hilo necesita su propia
   instancia del Builder.

5. Director acoplado al Builder concreto: agregar un nuevo
   Builder requeriría actualizar el Director. Una interfaz
   IContratoBuilder desacoplaría esto.

================================================================
