JVideo Viewer
=============================

A simple Java Viewer of V4L2 (Video for Linux 2) streaming.

## USAGE:

Clone the project

```

$> git clone https://github.com/gmpassos/jvideo-viewer.git
$> cd jvideo-viewer

```

Run using gradle:


```

$> ./gradlew run -PappArgs="8060 mjpeg"

```


## Java Command line:

Run viewer at port 8060:

```

$> java org.gracilianomp.jvideoviewer.JVideoViewer 8060 mjpeg

# or

$> java org.gracilianomp.jvideoviewer.JVideoViewer 8060 yuyv 640 480

```

## V4L2 Command:

Run capture streaming to IP 192.168.0.57 at port 8060:

```

$> v4l2-ctl --stream-mmap=3 --stream-count=100000 --stream-to-host 192.168.0.57:8060 --stream-lossless -v width=1280,height=720,pixelformat=MJPG

# or

$> v4l2-ctl --stream-mmap=3 --stream-count=100000 --stream-to-host 192.168.0.57:8060 --stream-lossless -v width=640,height=480,pixelformat=YUYV

```

## Author

Graciliano M. Passos
<gracilianomp@gmail.com>

## License

MIT license.
