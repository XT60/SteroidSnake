public enum Steroid {
    Testosteron, Proviron, Winstrol, MongolianSpecific;

    public double getDose(){
        return switch (this){
            case Testosteron -> 0.05;
            case Proviron -> 0.2;
            case Winstrol -> 0.75;
            case MongolianSpecific -> 0.99;
        };
    }
}
