package softwaremethodology.rutgerscafe;

import android.app.Application;

public final class GlobalInformation extends Application {

    private OrderArchive orderArchive = new OrderArchive();

    public OrderArchive getOrderArchive() {
        return orderArchive;
    }

    public void setOrderArchive(OrderArchive orderArchive){
        this.orderArchive = orderArchive;
    }


}
