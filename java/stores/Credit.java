package stores;

public class Credit {
    private CastCredit[] cast;
    private CrewCredit[] crew;
    private int id;

    public Credit(CastCredit[] cast, CrewCredit[] crew, int id) {
        this.cast = cast;
        this.crew = crew;
        this.id = id;
    }

    public CastCredit[] getCast() {
        return cast;
    }

    public void setCast(CastCredit[] cast) {
        this.cast = cast;
    }

    public CrewCredit[] getCrew() {
        return crew;
    }

    public void setCrew(CrewCredit[] crew) {
        this.crew = crew;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
}
