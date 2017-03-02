package com.yuhuayuan.core.dto.weixin;


//
public class QRCodeRequest {
	private String action_name;
	private ActionInfo action_info;
	
	public static class ActionInfo
	{
		private Scene scene;

		public Scene getScene() {
			return scene;
		}

		public void setScene(Scene scene) {
			this.scene = scene;
		}
	}
	
	public static class Scene
	{
		private String scene_str;

		public String getScene_str() {
			return scene_str;
		}

		public void setScene_str(String scene_str) {
			this.scene_str = scene_str;
		}
	}

	public String getAction_name() {
		return action_name;
	}

	public void setAction_name(String action_name) {
		this.action_name = action_name;
	}

	public ActionInfo getAction_info() {
		return action_info;
	}

	public void setAction_info(ActionInfo action_info) {
		this.action_info = action_info;
	}
}
