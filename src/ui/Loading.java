package ui;

import javax.swing.JLabel;
import java.awt.Color;
import javax.swing.ImageIcon;

public class Loading extends JLabel {
	/**
	 *
	 */
	private static final long serialVersionUID = 8769734065406027263L;
	private static ImageIcon icon;
	private static Loading loading;

	static {
		try {
			icon = new ImageIcon(Loading.class.getResource("../res/ajax-loader.gif"));
			System.out.println(icon);
			if (icon == null) {
				throw new Exception();
			}
		} catch (Exception e) {
			System.out.println("未找到资源,请重试或联系管理员!");
		}
	}

	public static class Builder {
		private Size size =new Size();
		private Color color = Color.white;
		private boolean enabled = true;
		private boolean visible = true;
		public Builder setSize(int width, int height) {
			size.width = width;
			size.height = height;
			return this;
		}

		public Builder setBackground(Color color){
			this.color=color;
			return this;
		}

		public Builder setEnabled(boolean enabled){
			this.enabled = enabled;
			return this;
		}

		public Builder setVisible(boolean visible){
			this.visible=visible;
			return this;
		}
		public Loading build(){
			loading = new Loading();
			loading.setBackground(color);
			loading.setEnabled(enabled);
			loading.setVisible(visible);
			loading.setSize(size.width,size.height);
			return loading;
		}


		static class Size {
			int width = 100;
			int height = 100;
		}
	}

	private Loading() {
		new Loading("Loading...", icon, JLabel.CENTER);
	}

	private Loading(String msg, ImageIcon icon, int constants) {
		super(msg, icon, constants);
	}

	public static Loading loadNow(String... msg) {
		if (msg.length <= 1)
			loading = new Loading("Loading...", icon, JLabel.CENTER);
		else {
			String tms = "";
			for (String string : msg) {
				tms += msg;
			}
			loading = new Loading(tms, icon, JLabel.CENTER);
		}
		return loading;
	}

}
