package javaStream;


import java.util.Arrays;
import java.util.Comparator;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import java.util.stream.Stream;

/*
 * 간단한 예제를 통해서 이해해 보아요!
 * 직원 객체를 생성해서 ArrayList안에 여러명의 직원을 저장.
 * 이 직원중에 IT에 종사하고 남자인 직원을 추려서 해당 직원들의 연봉 평균을 출력
 */
class Exam03_Employee implements Comparable<Exam03_Employee>{
	private String name;
	private int age;
	private String dept;
	private String gender;
	private int salary;
	
	public Exam03_Employee() {}
	
	public Exam03_Employee(String name, int age, String dept, String gender, int salary) {
		super();
		this.name = name;
		this.age = age;
		this.dept = dept;
		this.gender = gender;
		this.salary = salary;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public int getAge() {
		return age;
	}
	public void setAge(int age) {
		this.age = age;
	}
	public String getDept() {
		return dept;
	}
	public void setDept(String dept) {
		this.dept = dept;
	}
	public String getGender() {
		return gender;
	}
	public void setGender(String gender) {
		this.gender = gender;
	}
	public int getSalary() {
		return salary;
	}
	public void setSalary(int salary) {
		this.salary = salary;
	}
	
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + age;
		result = prime * result + ((dept == null) ? 0 : dept.hashCode());
		result = prime * result + ((gender == null) ? 0 : gender.hashCode());
		result = prime * result + ((name == null) ? 0 : name.hashCode());
		result = prime * result + salary;
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		// 만약 overriding을 하지 않으면 메모리 주소가지고 비교 
		// 즉, 내용은 같은 객체를 중복제거 불가 
		// overriding 해줘야 중복제거 가능
	
		// overriding을 해서 특정 조건을 만족하면
		// 객체가 같아!! 라는 식으로 작성해 보아요!!
		boolean result = false;
		
		Exam03_Employee target = (Exam03_Employee)obj; // 객체 케스팅. 다운캐스팅 obj -> Exam03_Employee
		if(this.getName().equals(target.getName())) {
			result = true;
		} else {
			result = false;
		}
		return result;
	}

	@Override
	public int compareTo(Exam03_Employee o) {
		// 정수값을 리턴. 
		// 양수가 리턴되면 순서를 바꿔요. 
		// 0이나 음수가 리턴되면 순서를 바꾸지 않아요 
		int result = 0;
		
		// 어떤 것을 가지고 정렬할지 명시 
		if(this.getSalary() >= o.getSalary()) {
			result = 1;
		}else if(this.getSalary() == o.getSalary()) {
			result = 0;
		} else {
			result = -1;
		}
		
		return result;
	}

}


public class Exam03_StreamPipeline {

	private static List<Exam03_Employee> employees =
			Arrays.asList(new Exam03_Employee("홍길동",20,"IT","남자",2000),
					new Exam03_Employee("김길동",30,"Sales","여자",3000),
					new Exam03_Employee("최길동",40,"IT","남자",1000),
					new Exam03_Employee("이순신",50,"Sales","남자",3500),
					new Exam03_Employee("유관순",35,"IT","여자",7000),
					new Exam03_Employee("신사임당",60,"IT","여자",4000),
					new Exam03_Employee("감강찬",30,"IT","남자",1000),
					new Exam03_Employee("이황",45,"Sales","남자",5000),
					new Exam03_Employee("홍길동",20,"IT","남자",2000));
	
	public static void main(String[] args) {
		
		// 부서가 IT인 사람들 중 남자에 대한 연봉 평균을 구해보세요
		Stream<Exam03_Employee> stream =employees.stream();
		
		// stream의 중간처리와 최종처리를 이용해서 원하는 작업을 해보아요
		// filter method는 결과값을 가지고 있는 stream을 리턴
		
		
		  double avg = stream.filter(t -> t.getDept().equals("IT")) 
				             .filter(t -> t.getGender().equals("남자"))
				             .mapToInt(t -> t.getSalary())
				             .average().getAsDouble();
		  
		  // lazy 처리 
		  System.out.println("IT부서의 남자평균 연봉 : " +avg );
		 
		
		//// 그럼 Stream이 가지고 있는 method는 무엇이 있나요?
		// 나이가 35 이상인 직원 중 남자 직원의 이름을 출력하세요
		
		  stream.filter(t -> (t.getAge() >= 35) ) // 정확히 말하면 35이상인 사람들만 모아서 새로운 stream을 만듬 
		        .filter(t -> t.getGender().equals("남자")) 
		        .forEach(t -> System.out.println(t.getName())); // 객체가 인자로 들어가고 무슨일을 할건지 람다식으로 표현
		 		
		
		//// 중복제거를 위한 중복처리
		
		// 숫자에 대한 중복처리
		
		  int temp[] = {10,20,30,40,50,30,40}; 
		  IntStream s = Arrays.stream(temp); 
		  //distinct() : 중복제거 후 새로운 stream 생성 
		  s.distinct().forEach(t -> System.out.println(t));
		 
		
		// List에 내용이 같은 객체를 추가해서 내용중복이 된 데이터에 대해 중복제거
		// 객체에 대한 중복제거를 해보자 
		
		  employees.stream() 
		  		   .distinct() 
		  		   .forEach(t -> System.out.println(t.getName()));
		 
		// mapToInt() => mapXXX()
		
		// 정렬( 부서가 IT인 사람을 연봉순으로 출력 )
		employees.stream()
				 .filter(t -> t.getDept().equals("IT"))
				 .distinct()
				 .sorted( Comparator.reverseOrder() ) // 오름차순 정렬 , .sorted( Comparator.reverseOrder() ) : 내림차순 정렬
				 .forEach(t -> System.out.println(t.getName() + "," +t.getSalary()) );
		
		
		// 반복
		// forEach()를 이용하면 스트림 안의 요소를 반복할 수 있어요
		// forEach()는 최종 처리 함수
		// 중간에 사용할 수가 없음
		// 사람들이 중간에 이것도 결과 뽑아서 보고싶고 등등... 그래서..
		// 중간 처리 함수로 반복처리하는 함수가 하나 더 제공
		// peek 
		// 그러나 peek은 중간처리 함수이기 때문에 최종처리가 나오기 전까지 lazy 된다 
		employees.stream()
				 .peek(t -> System.out.println(t.getName()))
				 .mapToInt(t -> t.getSalary())
				 .forEach(t -> System.out.println(t));
		
		// 확인용 최종 처리 함수 => predicate를 이용해서 boolean으로 return
		// 50살 이상인 사람만 추출해서 이름을 출력
		boolean result = employees.stream()
				                  .filter(t -> (t.getAge() >= 50))
				                  .allMatch(t -> (t.getAge() > 55));
		System.out.println(result);
		
		// 최종 황긴용 함수로 forEach를 많이 사용했는데
		// forEach말고 collect()를 이용해 보아요 
		// 나이가 50살 이상인 사람들의 연봉을 구해서
		// List<Interger>형태의 ArrayList에 저장해 보아요
		
		Set<Integer> tmp = employees.stream()
				 .filter(t -> (t.getAge() >= 50))
				 .map(t -> t.getSalary())
				 //.collect(Collectors.toList());
				 .collect(Collectors.toCollection(HashSet :: new));
		
		System.out.println(tmp);
		// 당연히 Set으로도 저장할 수 있고요.. Map으로도 저장할 수 있어요.
		
		
	}
}
