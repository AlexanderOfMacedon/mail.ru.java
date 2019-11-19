package mail.ru;

public enum TradeType {
    FX_SPOT {
        public SimpleTrade getTrade(int price) {
            return new Fx_spot(price);
        }
    },

    BOND {
        public SimpleTrade getTrade(int price) {
            return new Bond(price);
        }
    };

    public abstract SimpleTrade getTrade(int price);

}