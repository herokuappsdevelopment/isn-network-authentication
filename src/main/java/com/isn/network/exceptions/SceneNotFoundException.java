package com.isn.network.exceptions;

 public class SceneNotFoundException extends RuntimeException  {
	 
	 /**
	 * 
	 */
	private static final long serialVersionUID = 8022113326398776163L;

	public SceneNotFoundException(Long id){
		 super("Could not find Scene with ID: " + id);
	 }

}
