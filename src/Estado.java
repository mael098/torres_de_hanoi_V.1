public class Estado {

    public Torre[] torres;
    public Estado padre;
    public String movimiento;

    public Estado(Torre t1, Torre t2, Torre t3) {

        torres = new Torre[] {
            t1,
            t2,
            t3
        };
    }

    public String generarclave() {

        return torres[0].toString() + "|" +
               torres[1].toString() + "|" +
               torres[2].toString();
    }

    @Override
    public String toString() {

        return
            "T1: " + torres[0] + "\n" +
            "T2: " + torres[1] + "\n" +
            "T3: " + torres[2] + "\n";
    }
}