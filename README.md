---
# 💻최적화 알고리즘을 이용한 회귀식 추정💻 
---
**[201901727 정보통신공학과 한승철]**<br><br>


이번 보고서는 **최적화 알고리즘**을 이용해 **회귀식**을 추정하고 분석하는 과정을 담으려고한다. <br>
하지만 아무런 배경 지식없이 알고리즘을 통해 **회귀식**을 추정하는 과정만 보인다면 의미가 없을테니 <br>
**회귀식**이 무엇인지부터 이것을 분석하는 최적화 알고리즘을 어떤것이 있고 , 어떻게 구현하는지 까지 자세히 알아보려고 한다.

### 목차 
**🏴회귀분석 , 회귀식이란?**            
└ 내가 설정한 회귀분석 변수 모델 ┘

**🏴사용할 최적화 알고리즘** <br>
└ 모의 담금질 기법에 대하여 ┘ 

**🏴실제 구현해보기** <br>
└ 반복 루프 수에 따른 최적해의 변화 ┘ <br>

**🏴마무리** <br><br><br>

---


---

## 🏴회귀분석 , 회귀식이란? 

**회귀(regression)** 라는 말의 뜻은 무엇일까?  사전적 의미는 'go back to an earlier and worse condition' 이라는 뜻으로 <br>
이전의 대표적인 상태로 돌아간다는 의미이다. 이전의 대표적인 상태로 돌아간다는게 무엇을 뜻할까? <br>
예시를 통해 한번 알아보도록 하자. 우리나라 사람들의 평큔 키를 170cm라고 가정하자. <br> 
큰 키를 가진사람 , 평균 키를 가진사람 , 키가 작은사람 등 여러사람들이 결혼을 하고 애를 낳으면서 세대가 거듭되는데 , <br>
이렇게 계속해서 세대가 거듭되어도 결국 그 자손들의 키도 평균 170cm에 가까워진다는 말이다. <br>
자손이 계손 퍼지고 , 세대를 거듭해도 결국 처음 평균이 170cm로 돌아가려 하고 가까워지는 이것은 **회귀(regression)** 한다고 한다. <br><br>

**회귀분석** 은 변수들 사이의 인과관계를 규명하고자 하는 분석방법으로 , 변수들간의 나타나는 경향성을 설명하는 것이 주 목적이다. <br>
그렇기 때문에  **회귀분석** 을 실행할 때는 , 변수들 사이의 함수적인 관련성을 규명하기 위해 어떤 **수학적 모형** 을 가정하고 , 이 모형을 측정된 변수들의 자료로부터 추정한다. 여기서 사용되는 수학적인 모형 , 식을 우리는 **회귀식** 이라고 표현한다. <br><br>

**회귀분석** 도 경우에 따라 해석도 , 분석법도 다르다. <br>
우리는 항상 어떤 결과를 받아들일 때 , 꼭 원인이 하나라고 단정지어 생각하지 않는다. <br>
내 몸상태를 예로 들어보자면 , 내가 지금 피곤한 이유는 , 어제 시험 공부하느라 잠을 늦게 자서 그런걸 수 도 있고 , 아르바이트를 하면서 힘을 많이 써서 그런 걸 수도 있다. 즉 하나의 결과를 만들기까지 다양한 원인이 있을 수도 있다는 말이다. **회귀분석** 도 마찬가지다. <br>

**▶ 단순회귀분석(simple regression): 한 가지 원인이 한가지의 결과에 영향을 미친다 ◀** <br>
**▶ 다중회귀분석(multiple regression): 두 가지 이상의 원인이 한가지의 결과에 영향을 미친다 ◀** <br>

**→다중회귀분석(multiple regression)** 같은 경우에는 원인이 많아질수록 변수도 그만큼 늘어나고 , 그 만큼 어렵고 분석이 힘들어진다.<br>
그래서 이번 보고서에서는 한 가지 원인이 한 가지의 결과에만 영향을 미치는 **단순회귀분석(simple regression)** 을 할 것이다.<br><br>

**회귀분석**에서 다른 변수에 영향을 주는 원인에 해당하는 변수는 **독립변수**<br>
이러한 **독립변수**에 영향을 받는 결과에 해당하는 변수를 **종속변수** 라고 한다.


![독립변수 종속변수 관계](https://user-images.githubusercontent.com/101388379/174141645-82dbac6c-16c0-4d30-888a-cfcc679ad128.PNG)

<br>

**이제 우리가 사용할 수학적 모형 , 회귀식이다.** <br>
![회귀식 사진](https://user-images.githubusercontent.com/101388379/174155988-9efbf208-5cae-4633-802d-05583035af07.PNG)


 ![y](https://user-images.githubusercontent.com/101388379/174143375-6a1efcd8-7541-4813-80dc-49aa225e0c0e.PNG)
 **(종속변수 , 결과값에 해당한다.)**
 
 
![B0](https://user-images.githubusercontent.com/101388379/174143556-c5e481ab-3798-4fa2-aca4-50fc642c6b9c.PNG)
**(절편)**

![BX](https://user-images.githubusercontent.com/101388379/174143645-fcebc3df-b610-4f8e-92c7-94f61a476d9e.PNG)
**(독립변수 , 원인에 해당한다.)** <br><br>


여기서 중요한 건 **종속변수**와 **독립변수** 라는걸 알아두자. <br>
**회귀분석**은 변수들 사이의 인과관계를 규명하고자 하는만큼 , 변수의 역할설정이 굉장히 중요하다. <br>
이 점을 유의하자.

---
---
## 🏴내가 설정한 회귀분석 변수 모델

요즘 사람들은 유튜브를 굉장히 애용한다. 그건 나도 마찬가지다. 모르는 것을 쉽게 알려주는 강의영상도 많고 , 재미를 추구하는 다양한 영상도 굉장히 많다.
하지만 이런 유튜브 영상 공짜라고 생각되지만 , 우리는 반강제적으로 나오는 광고를 보며 그 값을 지불하고 있는 것이다.<br>
나는 항상 그런 광고를 보며 생각했다. 5초면 넘길 수 있는 광고 , 보던걸 끊기게 하는 짜증나는 광고인데, 누가 광고를 보고 구매욕이 돋을지에 대해서 말이다.
이런 광고비에 돈을 쓴다고 매출이 드라마틱하게 오르기는 할지 궁금해 했었다. <br>

그래서 제품도 많이 가지고 있고 , 주식도 가지고 있어서 흥미가 있는, 삼성전자의 마케팅비에 따른 매출액을 조사해서 이 데이터 값들로 회귀식을 추정해보려고 한다. 
아래표에 데이터값들을 정리해놓았다.

### 🚩설정 변수 모델: 2000년부터 2020년까지 삼성전자의 마케팅비에 따른 매출액 <br>
### 🏳️독립변수(원인) : 2000년부터 2020년까지 삼성전자가 마케팅에 사용한 비용 <br>
### 🏳️종속변수(결과) : 2000년부터 2020년까지 삼성전자의 매출액 <br><br>



|🚩년도(단위/년)|🏳️마케팅비(단위/조)[독립변수]|🏳️매출액(단위/조)[종속변수]|
|------|---|---|
|**2000**|0.8|34|
|**2001**|1.2|46|
|**2002**|1.3|40|
|**2003**|1.5|64|
|**2004**|1.8|81|
|**2005**|2|80|
|**2006**|2.3|85|
|**2007**|3|98|
|**2008**|4.7|121|
|**2009**|5.5|136|
|**2010**|7|154|
|**2011**|8.2|165|
|**2012**|10.2|201|
|**2013**|14|230|
|**2014**|13|206|
|**2015**|10.9|200|
|**2016**|11.5|201|
|**2017**|12.6|240|
|**2018**|11|243|
|**2019**|11|230|
|**2020**|10.3|236|
<br>

**위의 데이터들은 삼성전자에서 공식적으로 발표한 값들이며 , 직접 모은 값들이다.** <br>

사실 데이터값이 있으면 SPSS 또는 Excel을 통해서도 회귀그래프를 그리고 회귀식을 구할 수 있다. <br>
그래서 나는 Excel로 회귀그래프를 그리며 회귀식을 먼저 구한 후 , 알고리즘이 과연 얼마나 정확하게 회귀식을 추정하는지 알아보는 방향으로<br>
보고서를 전개하려고 한다. <br>

**위의 데이터 값들 그래프 위의 한 점으로 나타내보았다.**<br><br>
![그래프 분산](https://user-images.githubusercontent.com/101388379/174152556-615067dd-8c95-4307-b719-e6136e2475ac.PNG)
<br>

**마케팅비가 같아도 매출액에 차이가 있을때도 있고 , 마케팅비를 더 써도 매출액이 더 적게 나온 년도도 있지만** <br>
**대략적으로 표를 보면 결국 마케팅비가 많을 수록 매출액도 많다는 걸 볼 수 있다.** <br>

**이 점들(데이터들)을 이용해 회귀선을 만들고 , 회귀식도 구할 수 있다. 아래 그래프를 살펴보자**<br><br>
![추세선 회귀식](https://user-images.githubusercontent.com/101388379/174155544-f049f77b-f0fe-4e04-8249-caee2a8a14d7.PNG) <br>

 **그래프를 통해 알 수 있듯이 이 식이 우리가 알고리즘을 통해 추정해야할 회귀식이다.** <br>
![회귀식](https://user-images.githubusercontent.com/101388379/174156302-f97ecee5-e31f-4ccd-b712-b253a9a9b2f9.PNG) <br> 
**이제는 알고리즘을 통해 분석방법을 알아보고 , 회귀식을 직접 추정해보도록 하자.**



---
## 🏴최적화 알고리즘 , 모의 담금질 기법에 대하여
**모의 담금질(Simulated Annealing)** 은 금속 공학의 담금질에서 왔다고 한다. <br> 
이 담금질은 금속재료를 가열한 다음 조금씩 냉각해 결정을 성장시켜 그 결함을 줄이는 작업이다. <br>
높은 온도에서 액체 상태인 물질이 온도가 점차 낮아지면서 결정체로 변하는 과정을 모방한 최적화 알고리즘이다.<br>
**열이 가해진 후, 운동에너지가 높아져서 물질의 분자가 자유로이 움직이는 것을 모방한 것이기에 해를 탐색하는 과정도 특정한 패턴이 없고 온도가 낮아지면서 분자의 움직임도 줄어들어 결정체가 되는 것을 모방하여 해 탐색 과정도 점점 더 규칙적인 방식으로 이루어진다.** <br>

교재에 나와있는 모의 담금질 기법의 알고리즘은 다음과 같다.
```
1.임의의 후보 s를 선택한다.

2.초기 온도 T를 정한다.

repeat 
 for i = 1 to kt { //kt는 T에서의 for - 루프 반복 횟수이다.
  s의 이웃해 중에서 랜덤하게 하나의 해 s'를 선택한다.
  d = (s'의 값) - (s의 값)
  if(d<0) // 이웃해인 s'가 더 우수한 경우
   s ← s'
  else
   q ← (0,1) 사이에서 랜덤하게 선택한 수
  if(q < p) s ← s' // p는 자유롭게 탐색할 확률이다.
 }T←aT //1보다 작은 상수 a를 T에 곱하여 새로운 T를 계산한다.
 until(종료 조건이 만족될 때까지)
 return s
 ```
 <br> 
 
**이번에는 그림을 통해 알고리즘의 작동 원리와 개념을 좀 더 쉽게 알아보겠다.** <br><br>

**●여기 무작위로 배정된 16개의 데이터가 있다고 하자●**
![모의 담금질 설명 1](https://user-images.githubusercontent.com/101388379/174195900-d28de2fc-2d58-465e-8513-424cb53cc398.PNG)

**1. 16개의 데이터를 랜덤으로 3개의 집단으로 나눈다. 그리고 평가값(1)을 구한다**
![모의 담금질 설명2](https://user-images.githubusercontent.com/101388379/174196221-fba8848b-578a-479a-9751-e2c72b850bc6.PNG)

**2. 데이터 중 하나를 임의로 선택해 , 다른 집단으로 옮긴 후 , 평가값(2)를 구한다. [이웃해를 구한다]** <br> 
**보면 (12 , 4) 에 위치해있던 노란색 데이터가 파란색이 된 걸 볼 수 있다.**
![모의 담금질 설명3](https://user-images.githubusercontent.com/101388379/174196815-2bcad3bf-1e55-4469-87e7-2e3cbc13551c.PNG)
<br>

**3. 평가값(1)과 평가값(2)를 비교했을때 , 평가값(2)가 더 작으면 , 이웃해를 새로운 해로 받아들인다.**

**4.동일한 온도 T에서 위의 과정을 t회 반복 한다. 온도 T는 △T만큼 변화시켜주고** <br>
**T인 온도가 임의의 0.001(특정온도)과 같은 온도보다 낮아지면 break 해준다.**<br>

---
---
## 실제 구현해보기

**이제 위에서 알고리즘의 작동방식에 대해서도 알아봤으니 , 실제로 구현해보고 분석하는 과정을 거쳐보자.**
<br><br>

제일 먼저 **Interface Problem** 을 구현하도록 한다. <br>
이  **Interface Problem** 안에는 **문제의 적합도를 판별할 fit 메소드** <br>
**현재의 후보해와 이웃해의 적합도를 비교하는 isNeighborBetter 메소드** 를 생성해준다.
```java
public interface Problem {
    
    double fit(double x);                                     //문제의 적합도 판별
    boolean isNeighborBetter(double f0, double f1);           //후보해와 이웃해의 적합도 비교

}
```
<br>

두번째로 **class SimulatedAnnealing** 을 구현해보자.
```java
public class SimulatedAnnealing {
    private int niter;
    public ArrayList<Double> hist; 

    public SimulatedAnnealing(int niter) {
        this.niter = niter;
        hist = new ArrayList<>();
    }

    public double solve(Problem p, double t, double a, double lower, double upper) {
        Random r = new Random();
        double x0 = r.nextDouble() * (upper - lower) + lower; //
        return solve(p, t, a, x0, lower, upper);
    }
 ```
   
  **최적해 도달까지 변화한 적합도 값을 저장한다.**
  
  **solve 메소드에 초기 후보해를 인수로 전달 해 주지 않을 경우 지정**  <br>
  **그리고 초기 후보해를 넣어 오버로딩한 solve 메소드를 호출한다.**
  
  ```java
   public double solve(Problem p, double t, double a, double x0, double lower, double upper) {
        Random r = new Random();
        double f0 = p.fit(x0);
        hist.add(f0);
        if (a >= 1) {
            a = 0.99;
        } 
        for (int i = 0; i < niter; i++) {
            int kt = (int) t; 
            for (int j = 0; j < kt; j++) {
                double x1 = r.nextDouble() * (upper - lower) + lower; 
                double f1 = p.fit(x1);
```

**위의 설명한 알고리즘에서 보았듯이 T는 갈수록 0에 가까워져야 하므로 냉각률이 1이상일 경우 a=0.99로 설정한다.** <br>
**온도 t에서의 for-루프 반복 횟수는 kt. 이때 , t가 낮아질수록 for - 루프 반복 횟수도 작아진다.** <br>
**이웃해를 선택한다**
  
```java
if (p.isNeighborBetter(f0, f1)) {
                    x0 = x1;
                    f0 = f1; 
                    hist.add(f0);
                } else { 
                    double d = Math.abs(f1 - f0);
                    double p0 = Math.exp(-d / t);
                    if (r.nextDouble() < p0) {                  
                        x0 = x1;
                        f0 = f1; 
                        hist.add(f0);
                    }
                }
            }
            t *= a;
        }
        return x0;
    }
}
```

**이웃해의 적합도가 더 높을 경우 이웃해를 후보해로 선택한다** <br>
**좋지 않은 이웃해를 선택할 확률 p0는 t에 반비례 , d(후보해와 이웃해 차이)와 비례**<br>
**현재 후보해의 적합도가 높더라도 자유롭게 탐색할 확률에 따라 좋지 못한 이웃해를 선택** <br><br>

세번째로 , 선형 모델 데이터의 가장 적합한 파라미터를 찾는 **main**을 구현한다.
```java
public class Main {
    public static void main(String[] args) {
        SimulatedAnnealing sa = new SimulatedAnnealing(1000);
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
        double a = sa.solve(p1, 100, 0.99, 2000, 2000, 5000);
        System.out.println("\ny=ax 선형 모델에 가장 적합한 파라미터");
        System.out.println("a : " + a);
     

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
        double b = sa.solve(p2, 100, 0.99, 2000, 2000, 5000);
        System.out.println("\ny=ax+b 선형 모델에 가장 적합한 파라미터");
        System.out.println("a : " + a);
        System.out.println("b : " + b);
        
    }
}
```
<br><br>


``` java
double[][] data = {{0.8, 34}, {1.2, 46}, {1.3, 40}, {1.5, 64}, {1.8, 81}, {2, 80}, {2.3, 85}, {3, 98}, {4.7, 121}, {5.5, 136}, {7, 154},
                {8.2 , 165} , {10.2 , 201} , {14,230} , {13,206} ,{10.9,200} , {11.5 , 201} , {12.6 , 240} , {11 ,243} , {11,230} , {10.3 , 236}};
```



**배열 값에 조사했던 삼성전자의 마케팅비와 매출액을 넣어줬다.**

---

---

## 🏴실행 결과 및 분석[반복 루프 횟수에 따른 최적해 값의 변화]

**이 코드에서 niter는 최적해를 구하기 위한 전체 반복 루프 횟수를 나타내고 있는데 , niter의 값을 조정하며 최적해가 어떻게 변해가는지** <br>
**한번 보면서 회귀식을 추정해보도록 하자.**
<br>

**먼저 niter 값이 1일때이다.**

```java
niter = 1            // 최적해를 구하기 위한 전체 반복 루프 횟수 1번 
```
**한번만으로 최적해를 구하는건 있을 수 없다. 그래서 터무니 없는 값이 나올 것으로 예상했다.** <br>
**실제로 값은 어떻게 나왔는지 보자.**

### 🚩실제 측정결과
![니터 한번일때 1](https://user-images.githubusercontent.com/101388379/174223371-d8bd2d59-bab2-47ee-aa08-56ead7e684c5.PNG)
![니터 한번일때 2](https://user-images.githubusercontent.com/101388379/174223378-3751036e-d337-4585-bd25-739836ca0b30.PNG) <br>
![니터 한번일때 3](https://user-images.githubusercontent.com/101388379/174223391-cba88b2c-4f5e-4ce4-85d1-430313bda871.PNG)
![니터 한번일때 4](https://user-images.githubusercontent.com/101388379/174223398-336f62d4-9e7d-4925-b9a5-fe782c55a93f.PNG)
<br>

▶**값의 편차가 굉장히 많이 나는걸 알 수 있다. 회귀식 추정을 물론이고 경향성 추측도 힘들다.**


---

<br><br>

**이번에는 niter 값을 100으로 설정한다.**

```java
niter = 100            // 최적해를 구하기 위한 전체 반복 루프 횟수 100번 
```

### 🚩실제 측정결과
![니터100일때 1](https://user-images.githubusercontent.com/101388379/174224184-632e412d-92c0-4a47-b5ad-dce653d48568.PNG)
![니터100일때 2](https://user-images.githubusercontent.com/101388379/174224196-c4c5f9d7-a991-440f-9976-a007c973d45e.PNG) <br>
![니터100일때 3](https://user-images.githubusercontent.com/101388379/174224201-b4720bd3-03be-4f19-acb1-b3f2629602cc.PNG)
![니터100일때 4](https://user-images.githubusercontent.com/101388379/174224209-7225d7da-2796-4120-a0cd-337770259d2d.PNG)
<br>


▶**실제 회귀식 a , b의 값과 차이는 있지만 , 값의 편차가 많이 줄어들었다. 어느정도 비슷한 범위에서 측정이 된다는걸 추측할 수 있다.**

<br><br>

---

**이번에는 niter 값을 10000으로 설정한다.**

```java
niter = 10000            // 최적해를 구하기 위한 전체 반복 루프 횟수 10000번 
```

### 🚩실제 측정결과
![니터10000일때 1](https://user-images.githubusercontent.com/101388379/174225491-32b51bc9-6731-4e26-ac69-ceeff2db566e.PNG)
![니터10000일때 2](https://user-images.githubusercontent.com/101388379/174225499-0b63e5d2-0f95-4d95-b3df-f97862573ba3.PNG)<br>
![니터10000일때 3](https://user-images.githubusercontent.com/101388379/174225502-da9c434e-9e7a-4566-9f2a-be3111c06f00.PNG)
![니터10000일때 4](https://user-images.githubusercontent.com/101388379/174225504-8f2a23c4-bc52-4998-b5bc-f645834b9a3a.PNG)

▶**한번씩 튀는 값들이 나오지만 평균적으로 봤을때 , 편차가 매우 줄어들었다. a는 거의 +-1로 값이 측정되고 있다.**

<br><br>

---

**마지막으로 niter 값을 1000000으로 설정해보자.**
```java
niter = 1000000            // 최적해를 구하기 위한 전체 반복 루프 횟수 1000000번 
```
<br>
**이번에는 마지막이니 , 확실히 경향성을 파악하고자 프로그램을 돌려봤고 , 8개의 값을 가져왔다.**

### 🚩실제 측정결과
![1](https://user-images.githubusercontent.com/101388379/174226406-7cb64d34-64ff-4c46-86a6-d7ad78c86fab.PNG)
![2](https://user-images.githubusercontent.com/101388379/174226416-650ebd97-dddb-407e-9317-5c45d6ccc1f7.PNG)<br>
![3](https://user-images.githubusercontent.com/101388379/174226430-554111e2-9adf-4c09-94f6-998b4f9dbf50.PNG)
![4](https://user-images.githubusercontent.com/101388379/174226434-67f9a9fc-e622-4851-b4e1-2b23783eceb1.PNG)<br>
![5](https://user-images.githubusercontent.com/101388379/174226439-07f44795-8fe5-433b-85c2-071c2e030de4.PNG)
![6](https://user-images.githubusercontent.com/101388379/174226442-1908a3cd-889f-4aa0-aeec-6b66521953bb.PNG)<br>
![7](https://user-images.githubusercontent.com/101388379/174226445-c7527b3f-8909-42af-9020-ab4e61480df9.PNG)
![8](https://user-images.githubusercontent.com/101388379/174226451-f2db625c-2b63-42f7-b739-eeb10fbff688.PNG)<br> 

**▶20번 중 1번 튀는 값이 나왔고 , 나머지 값들은 편차가 거의 없었다. a = 19에서 +-0.5 정도의 편차를 보이고** <br>
**b 같은 경우에는 a에 비해 편차가 조금 나타나는 편이지만 ,경향성을 파악하는데 전혀 문제가 없다.** 

---

**이제 위에서 얻은 데이터들로 회귀식을 추정해보도록 한다.** <br>
**측정된 a값과 b값들의 평균을 새로운 회귀식 a, b에 대입하였다.** <br>

![새로운 회귀식](https://user-images.githubusercontent.com/101388379/174227500-71688d68-b5fd-43c2-8c84-f879f74ec473.PNG) <br>

**결론적으로 이러한 회귀식을 우리는 추정할 수 있다.**
