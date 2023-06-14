import makehuman.model.Model;
import makehuman.model.ModelBuilder;

public class MakeHuman extends Main {

    public static void main(String[] args) {
        // Create a new model builder
        ModelBuilder builder = new ModelBuilder();

        // Create a new model
        Model model = builder.createModel();

        // Add a head to the model
        model.addHead();

        // Add a body to the model
        model.addBody();

        // Add a pelvis to the model
        model.addPelvis();

        // Add a spine to the model
        model.addSpine();

        // Add a spine to the model
        model.addSpine();

        // Add a spine to the model
        model.addSpine();

        // Save the model to a file
        model.save("my_model.obj");
    }
}
