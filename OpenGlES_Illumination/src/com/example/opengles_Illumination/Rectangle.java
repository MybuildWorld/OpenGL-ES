package com.example.opengles_Illumination;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.FloatBuffer;

import android.opengl.GLES20;

public class Rectangle {
	private FloatBuffer mVertexBuffer;
	private int mProgram;
	private int mPositionHandle;
	private int muMVPMatrixHandle;
	private int mColorHandle;

	public Rectangle(float r) {
		initVetexData(r);
	}

	public void initVetexData(float i) {
		float vertices[] = new float[] {
	        	//前面
	        	 0, 0, 1,  1,1,1,0,//中间为白色
	        	 1, 1, 1,  1,0,0,0,
	        	-1, 1, 1,  1,0,0,0,
	        	 0, 0, 1,  1,1,1,0,//中间为白色
	        	-1, 1, 1,  1,0,0,0,
	        	-1,-1, 1,  1,0,0,0,
	        	 0, 0, 1,  1,1,1,0,//中间为白色
	        	-1,-1, 1,  1,0,0,0,
	        	 1,-1, 1,  1,0,0,0,
	        	 0, 0, 1,  1,1,1,0,//中间为白色
	        	 1,-1, 1,  1,0,0,0,
	        	 1, 1, 1,  1,0,0,0,
	        	 //后面
	        	 0, 0,-1,  1,1,1,0,//中间为白色
	        	 1, 1,-1,  1,0,0,0,
	        	 1,-1,-1,  1,0,0,0,
	        	 0, 0,-1,  1,1,1,0,//中间为白色
	        	 1,-1,-1,  1,0,0,0,
	        	-1,-1,-1,  1,0,0,0,
	        	 0, 0,-1,  1,1,1,0,//中间为白色
	        	-1,-1,-1,  1,0,0,0,
	        	-1, 1,-1,  1,0,0,0,
	        	 0, 0,-1,  1,1,1,0,//中间为白色
	        	-1, 1,-1,  1,0,0,0,
	        	 1, 1,-1,  1,0,0,0,
	        	//左面
	        	-1, 0, 0,  1,1,1,0,//中间为白色
	        	-1, 1, 1,  1,0,0,0,
	        	-1, 1,-1,  1,0,0,0,
	        	-1, 0, 0,  1,1,1,0,//中间为白色
	        	-1, 1,-1,  1,0,0,0,
	        	-1,-1,-1,  1,0,0,0,
	        	-1, 0, 0,  1,1,1,0,//中间为白色
	        	-1,-1,-1,  1,0,0,0,
	        	-1,-1, 1,  1,0,0,0,
	        	-1, 0, 0,  1,1,1,0,//中间为白色
	        	-1,-1, 1,  1,0,0,0,
	        	-1, 1, 1,  1,0,0,0,
	        	//右面
	        	 1, 0, 0,  1,1,1,0,//中间为白色
	        	 1, 1, 1,  1,0,0,0,
	        	 1,-1, 1,  1,0,0,0,
	        	 1, 0, 0,  1,1,1,0,//中间为白色
	        	 1,-1, 1,  1,0,0,0,
	        	 1,-1,-1,  1,0,0,0,
	        	 1, 0, 0,  1,1,1,0,//中间为白色
	        	 1,-1,-1,  1,0,0,0,
	        	 1, 1,-1,  1,0,0,0,
	        	 1, 0, 0,  1,1,1,0,//中间为白色
	        	 1, 1,-1,  1,0,0,0,
	        	 1, 1, 1,  1,0,0,0,
	        	 //上面
	        	 0, 1, 0,  1,1,1,0,//中间为白色
	        	 1, 1, 1,  1,0,0,0,
	        	 1, 1,-1,  1,0,0,0,
	        	 0, 1, 0,  1,1,1,0,//中间为白色  	
	        	 1, 1,-1,  1,0,0,0,
	        	-1, 1,-1,  1,0,0,0,
	        	 0, 1, 0,  1,1,1,0,//中间为白色
	        	-1, 1,-1,  1,0,0,0,
	        	-1, 1, 1,  1,0,0,0,
	        	 0, 1, 0,  1,1,1,0,//中间为白色
	        	-1, 1, 1,  1,0,0,0,
	        	 1, 1, 1,  1,0,0,0,
	        	//下面
	        	 0,-1, 0,  1,1,1,0,//中间为白色
	        	 1,-1, 1,  1,0,0,0,
	        	-1,-1, 1,  1,0,0,0,
	        	 0,-1, 0,  1,1,1,0,//中间为白色
	        	-1,-1, 1,  1,0,0,0,
	        	-1,-1,-1,  1,0,0,0,
	        	 0,-1, 0,  1,1,1,0,//中间为白色
	        	-1,-1,-1,  1,0,0,0,
	        	 1,-1,-1,  1,0,0,0,
	        	 0,-1, 0,  1,1,1,0,//中间为白色
	        	 1,-1,-1,  1,0,0,0,
	        	 1,-1, 1,  1,0,0,0,
	        };

		ByteBuffer vbb = ByteBuffer.allocateDirect(vertices.length * 4);
		vbb.order(ByteOrder.nativeOrder());
		mVertexBuffer = vbb.asFloatBuffer();
		mVertexBuffer.put(vertices);
		mVertexBuffer.position(0);

		int vertexShader = loaderShader(GLES20.GL_VERTEX_SHADER,
				vertexShaderCode);
		int fragmentShader = loaderShader(GLES20.GL_FRAGMENT_SHADER,
				fragmentShaderCode);

		mProgram = GLES20.glCreateProgram();
		GLES20.glAttachShader(mProgram, vertexShader);
		GLES20.glAttachShader(mProgram, fragmentShader);
		GLES20.glLinkProgram(mProgram);

		mPositionHandle = GLES20.glGetAttribLocation(mProgram, "aPosition");
		mColorHandle = GLES20.glGetAttribLocation(mProgram, "aColor");
		muMVPMatrixHandle = GLES20.glGetUniformLocation(mProgram, "uMVPMatrix");
	}

	public void draw(float[] mvpMatrix) {
		GLES20.glUseProgram(mProgram);
		// 将顶点数据传递到管线，顶点着色器
		mVertexBuffer.position(0);
		GLES20.glVertexAttribPointer(mPositionHandle, 3, GLES20.GL_FLOAT, false, (4+3) * 4, mVertexBuffer);
		// 将顶点颜色传递到管线，顶点着色器
		mVertexBuffer.position(3);
        GLES20.glVertexAttribPointer(mColorHandle, 4, GLES20.GL_FLOAT, false, (4+3) * 4, mVertexBuffer);
		GLES20.glEnableVertexAttribArray(mPositionHandle);
		GLES20.glEnableVertexAttribArray(mColorHandle);
		GLES20.glUniformMatrix4fv(muMVPMatrixHandle, 1, false, mvpMatrix, 0);
		// 绘制图元
		GLES20.glDrawArrays(GLES20.GL_TRIANGLES, 0, 12*6);
	}

	private int loaderShader(int type, String shaderCode) {
		int shader = GLES20.glCreateShader(type);
		GLES20.glShaderSource(shader, shaderCode);
		GLES20.glCompileShader(shader);
		return shader;
	}

	private String vertexShaderCode = "uniform mat4 uMVPMatrix;"
			+ "attribute vec4 aColor;"
			+ "varying  vec4 aaColor;"
			+ "attribute vec3 aPosition;"
			+ "void main(){"
			+ "gl_Position = uMVPMatrix * vec4(aPosition,1);"
			+ "aaColor = aColor;"
			+ "}";

	private String fragmentShaderCode = "precision mediump float;"
			+ "varying  vec4 aaColor;"
			+ "void main(){"
			+ "gl_FragColor = aaColor;"
			+ "}";

}