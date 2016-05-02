package Nav_Utils;


public class NavDrawerItem {

        private boolean showNotify;
        private String title;
        private Integer images;


        public NavDrawerItem() {

        }

        public NavDrawerItem(boolean showNotify, String title, Integer imags) {
            this.showNotify = showNotify;
            this.title = title;
            this.images = imags;
        }

        public boolean isShowNotify() {
            return showNotify;
        }

        public void setShowNotify(boolean showNotify) {
            this.showNotify = showNotify;
        }

        public String getTitle() {
            return title;
        }
        public Integer getImages() {
            return images;
        }

        public void setTitle(String title) {
            this.title = title;
        }
        public void setImages(Integer images) {
            this.images = images;
        }
    }
