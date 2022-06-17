package SA;

public class Main {
    public static void main(String[] args) {
        SimulatedAnnealing sa = new SimulatedAnnealing(1000000);
        double[][] data = {{0.8, 34}, {1.2, 46}, {1.3, 40}, {1.5, 64}, {1.8, 81}, {2, 80}, {2.3, 85}, {3, 98}, {4.7, 121}, {5.5, 136}, {7, 154},
                {8.2 , 165} , {10.2 , 201} , {14,230} , {13,206} ,{10.9,200} , {11.5 , 201} , {12.6 , 240} , {11 ,243} , {11,230} , {10.3 , 236}};

        Problem p1 = new Problem() {
            @Override
            public double fit(double x) {
                double sum = 0;
                for (int i = 0; i < data.length; i++) {
                    double xv = data[i][0];
                    sum += Math.pow(x * xv - data[i][1], 2);
                }
                return sum / data.length;
            }

            @Override
            public boolean isNeighborBetter(double f0, double f1) {
                return f0 > f1;
            }
        };
        double a = sa.solve(p1, 100, 0.99, 2000, 0, 5000);
        System.out.println("");
        System.out.println("");


        Problem p2 = new Problem() {
            @Override
            public double fit(double x) {
                double sum = 0;
                for (int i = 0; i < data.length; i++) {
                    double xv = data[i][0];
                    sum += Math.pow((a * xv + x) - data[i][1], 2);
                }
                return sum / data.length;
            }

            @Override
            public boolean isNeighborBetter(double f0, double f1) {
                return f0 > f1;
            } //작은값선택
        };
        double b = sa.solve(p2, 100, 0.99, 2000, 0, 1000);
        System.out.println("\ny=ax+b 선형 모델에 적합한 파라미터");
        System.out.println("a : "+ a );
        System.out.println("b : "+ b );

    }
}