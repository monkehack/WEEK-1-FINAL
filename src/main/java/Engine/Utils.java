package Engine;
import org.joml.Vector3f;

import java.io.IOException;
import java.nio.file.*;
import java.util.List;

import static org.lwjgl.opengl.GL15.*;
import static org.lwjgl.opengl.GL20.glEnableVertexAttribArray;
import static org.lwjgl.opengl.GL20.glVertexAttribPointer;
import static org.lwjgl.opengl.GL30.glBindVertexArray;
import static org.lwjgl.opengl.GL30.glGenVertexArrays;

public class Utils {

    public static String readFile(String filePath) {
        String str;
        try {
            str = new String(Files.readAllBytes(Paths.get(filePath)));
        } catch (IOException excp) {
            throw new RuntimeException("Error reading file [" + filePath + "]", excp);
        }
        return str;
    }

    public static float[] listoFloat(List<Vector3f> arraylist){
        float[] arr = new float[arraylist.size()*3];
        int index = 0;
        for(int i = 0;i<arraylist.size();i++){
            arr[index++] = arraylist.get(i).x;
            arr[index++] = arraylist.get(i).y;
            arr[index++] = arraylist.get(i).z;
        }
        return arr;
    }

    public static int[] listoInt(List<Integer> arraylist){
        int[] arr = new int[arraylist.size()];
        for(int i = 0;i<arraylist.size();i++){
            arr[i] = arraylist.get(i);
        }
        return arr;
    }

    public static class Object2d extends ShaderProgram {

        List<Vector3f> vertices;

        int vao;
        int vbo;

        public Object2d(List<ShaderModuleData> shaderModuleDataList, List<Vector3f> vertices) {
            super(shaderModuleDataList);
            this.vertices = vertices;
            setupVAOVBD();
        }

        public void setupVAOVBD() {
            vao = glGenVertexArrays();
            glBindVertexArray(vao);

            vbo = glGenBuffers();
            glBindBuffer(GL_ARRAY_BUFFER, vbo);
            glBufferData(GL_ARRAY_BUFFER, listoFloat(vertices), GL_STATIC_COPY);
        }

        public void drawSetup() {
            bind();
            glEnableVertexAttribArray(0);
            glBindBuffer(GL_ARRAY_BUFFER, vbo);
            glVertexAttribPointer(0, 3, GL_FLOAT, false, 0, 0);
        }

        public void draw() {
            drawSetup();
            glLineWidth(1);
            glPointSize(0);
            glDrawArrays(GL_TRIANGLES, 0, vertices.size());
        }
    }
}
