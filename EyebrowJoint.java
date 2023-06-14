import java.util.ArrayList;
import java.util.List;

public class EyebrowJoint extends Main {

    private List<Vertex> vertices;
    private List<Bone> bones;

    public EyebrowJoint() {
        vertices = new ArrayList<>();
        bones = new ArrayList<>();
    }

    public void addVertex(Vertex vertex) {
        vertices.add(vertex);
    }

    public void addBone(Bone bone) {
        bones.add(bone);
    }

    public void move(float x, float y, float z) {
        for (Vertex vertex : vertices) {
            vertex.x += x;
            vertex.y += y;
            vertex.z += z;
        }
    }

    public void rotate(float x, float y, float z) {
        for (Vertex vertex : vertices) {
            vertex.x = (float) (vertex.x * Math.cos(x) - vertex.y * Math.sin(x));
            vertex.y = (float) (vertex.x * Math.sin(x) + vertex.y * Math.cos(x));
        }
    }

    public void scale(float x, float y, float z) {
        for (Vertex vertex : vertices) {
            vertex.x *= x;
            vertex.y *= y;
            vertex.z *= z;
        }
    }

    public void render() {
        for (Vertex vertex : vertices) {
            System.out.println("Vertex(" + vertex.x + ", " + vertex.y + ", " + vertex.z + ")");
        }
    }

}
