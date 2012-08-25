package core.model;

public interface State<AS extends Enum<AS>> {

	void execute(AS input);

}
