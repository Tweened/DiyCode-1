package com.plusend.diycode.mvp.model.notification.presenter;

import android.util.Log;
import com.plusend.diycode.mvp.model.base.BaseData;
import com.plusend.diycode.mvp.model.base.Presenter;
import com.plusend.diycode.mvp.model.notification.data.NotificationDataNetwork;
import com.plusend.diycode.mvp.model.notification.event.NotificationsEvent;
import com.plusend.diycode.mvp.model.notification.view.NotificationsView;
import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

public class NotificationsPresenter extends Presenter {
  private static final String TAG = "NotificationsPresenter";
  private BaseData topicData;
  private NotificationsView view;

  public NotificationsPresenter(NotificationsView view) {
    this.view = view;
    this.topicData = NotificationDataNetwork.getInstance();
  }

  public void readNotifications(int offset) {
    Log.d(TAG, "readNotifications");
    ((NotificationDataNetwork) topicData).readNotifications(offset, null);
  }

  @Subscribe(sticky = true, threadMode = ThreadMode.MAIN)
  public void showNotifications(NotificationsEvent event) {
    Log.d(TAG, "showNotifications");
    view.showNotifications(event.getNotificationList());
    EventBus.getDefault().removeStickyEvent(event);
  }

  @Override public void start() {
    EventBus.getDefault().register(this);
  }

  @Override public void stop() {
    EventBus.getDefault().unregister(this);
  }
}
