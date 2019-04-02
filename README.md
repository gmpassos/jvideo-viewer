JVideo Viewer
=============================

The project...

## USAGE:

Run viewer at port 8060:

```

$> java org.gracilianomp.jvideoviewer.JVideoViewer 8060 mjpeg

# or

$> java org.gracilianomp.jvideoviewer.JVideoViewer 8060 yuyv 640 480

```

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
