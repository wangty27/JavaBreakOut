import java.util.List;
import java.util.ArrayList;

public class Observerable {
  private List<Observer> obs = new ArrayList<>();
  public void notifyObservers() {
    for (Observer ob: this.obs) {
      ob.update();
    }
  }

  public void addObserver(Observer ob) {
    this.obs.add(ob);
  }
}