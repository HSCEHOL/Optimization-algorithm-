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

**🏴최적화 알고리즘에는 무엇이 있을까** <br>
└ 유전 알고리즘에 대하여 ┘ <br>
└ 모의 담금질 기법에 대하여 ┘ 

**🏴어떤 알고리즘으로 최적화를 진행할것인가** <br>
└ 선택 , 이유 ┘

**🏴실제 구현해보기** <br>
└ 이웃해 설정과 이유 ┘ <br>
└ 추정된 모수값이 적절한가 ┘ <br>
└ 최적화 과정에 대한 분석 ┘

**🏴마무리** <br><br><br>






---

## 회귀분석 , 회귀식이란? 

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
## 내가 설정한 회귀분석 변수 모델

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
![회귀식](https://user-images.githubusercontent.com/101388379/174156302-f97ecee5-e31f-4ccd-b712-b253a9a9b2f9.PNG)
**이제는 알고리즘을 통해 분석방법을 알아보고 , 회귀식을 직접 추정해보도록 하자.**
