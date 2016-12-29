package root.shaders;

public class StaticShader extends ShaderProgram {
	private static final String VERTEX_FILE = "";
	private static final String FRAGMENT_FILE = "";

	public StaticShader() {
		super(VERTEX_FILE, FRAGMENT_FILE);
	}

	@Override
	protected void getAllUniformLocatin() {
		
	}

	@Override
	protected void bindAttributes() {
		super.bindAttribute(0, "position");

	}

}
