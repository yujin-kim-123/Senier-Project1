package ssh.demo.main;

import ssh.demo.bean.SSHDemoBean;

public class SSHDemoMain {
	static String capacity, age, model, tire1, tire2, tire3, tire4, light1, light2, light3, light4, engine, brake,
			battery;

	String status_return = " ";

	public String input(String capacity, String age, String model, String tire1, String tire2, String tire3,
			String tire4, String light1, String light2, String light3, String light4, String engine, String brake,
			String battery) throws InterruptedException {
		this.capacity = capacity;
		this.age = age;
		this.model = model;

		this.tire1 = tire1;
		this.tire2 = tire2;
		this.tire3 = tire3;
		this.tire4 = tire4;

		this.light1 = light1;
		this.light2 = light2;
		this.light3 = light3;
		this.light4 = light4;

		this.engine = engine;
		this.brake = brake;
		this.battery = battery;

		ssh_connect();

		
		return status_return;
	}

	public void ssh_connect() throws InterruptedException {
		SSHDemoBean sshDemoBean = new SSHDemoBean();

		// 호스트 이름,패스워드 등등 입력해서 server02에 연결
		if (sshDemoBean.openConnection("root", "adminuser", "192.168.38.135", 22, 120000)) {
			System.out.println("connected to server \r\n");

			

			// 리눅스 명령어 입력
			sshDemoBean.sendCommand("cd /home/pilot-pjt/mahout-data/ \n");
			sshDemoBean.sendCommand(
					"java -cp bigdata.smartcar.mahout-1.0.jar com.wikibook.bigdata.smartcar.mahout.ClassifySmartCarStatus "
							+ capacity + " " + age + " " + model + " " + tire1 + " " + tire2 + " " + tire3 + " " + tire4
							+ " " + light1 + " " + light2 + " " + light3 + " " + light4 + " " + engine + " " + brake
							+ " " + battery + "\n");

			// 혹시 java -cp~ 명령어 입력시 결과가 안나오면 100단위로 시간 늘려보면 됌

			Thread.sleep(2500);

			String recvData = sshDemoBean.recvData();

			System.out.println("Result : " + recvData);
			// 정상적으로 실행됨을 콘솔창으로 확인

			if (recvData.contains(":비정상")) {
				status_return = "비정상";
				System.out.println(status_return);
			}

			if (recvData.contains(":정상")) {
				status_return = "정상";
				System.out.println(status_return);
			}

			// sshDemoBean.close();

		} else {
			System.out.println("can not connect to server");
		}
	}
}
