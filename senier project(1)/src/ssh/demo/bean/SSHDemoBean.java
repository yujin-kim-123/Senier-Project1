package ssh.demo.bean;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import com.jcraft.jsch.Channel;
import com.jcraft.jsch.JSch;
import com.jcraft.jsch.Session;

public class SSHDemoBean {
	private JSch jsch = null;
	private Session session = null;
	private Channel channel = null;

	private InputStream Input = null;
	private OutputStream Output = null;

	// 연결을 위한 이름, 번호, 호스트명, 포트번호 등등을 받아와 연결시도
	public boolean openConnection(String username, String password, String host, int port, int iTimeout) {
		boolean blResult = false;

		this.jsch = new JSch();

		java.util.Properties config = new java.util.Properties();
		config.put("StrictHostKeyChecking", "no");
		this.jsch.setConfig(config);

		try {
			this.session = this.jsch.getSession(username, host, port);

			this.session.setPassword(password);
			this.session.connect(iTimeout);

			this.channel = this.session.openChannel("shell");
			this.channel.connect();

			this.Input = this.channel.getInputStream();
			this.Output = this.channel.getOutputStream();

			blResult = true;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return blResult;
	}

	// 리눅스에 커맨드를 보냄
	public boolean sendCommand(String strCommand) {
		boolean blResult = false;

		try {
			if (this.Output != null) {
				this.Output.write(strCommand.getBytes());
				this.Output.flush();
			}
		} catch (Exception e) {
			e.printStackTrace();
		}

		return blResult;
	}

	// 커맨드 실행 후 결과 값 저장 메소드
	public String recvData() {
		String strData = "";

		// 결과값 저장되는 곳
		try {
			if (this.Input != null) {
				// 읽을 수 있는 바이트 수 반환
				int iAvailable = this.Input.available();
				while (iAvailable > 0) {
					byte[] btBuffer = new byte[iAvailable];
					// read() : 한바이트를 읽어서 반환
					int iByteRead = this.Input.read(btBuffer);

					iAvailable = iAvailable - iByteRead;
					strData = strData + new String(btBuffer);
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}

		return strData;
	}

	public void close() {
		if (this.session != null) {
			this.session.disconnect();
		}

		if (this.channel != null) {
			this.channel.isClosed();
		}

		if (this.Input != null) {
			try {
				this.Input.close();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}

		if (this.Output != null) {
			try {
				this.Output.close();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		this.jsch = null;
	}
}
