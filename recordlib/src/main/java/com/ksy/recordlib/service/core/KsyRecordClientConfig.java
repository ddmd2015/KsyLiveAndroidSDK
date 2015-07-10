package com.ksy.recordlib.service.core;

import android.hardware.Camera;
import android.media.CamcorderProfile;
import android.media.MediaRecorder;

import com.ksy.recordlib.service.exception.KsyRecordException;
import com.ksy.recordlib.service.util.Constants;

/**
 * Created by eflakemac on 15/6/17.
 */
public class KsyRecordClientConfig {

    public static int MEDIA_TEMP = 1;
    public static int MEDIA_SETP = 2;

    int mCameraType;
    int mVoiceType;
    int mAudioSampleRate;
    int mAudioBitRate;
    int mAudioEncorder;
    int mVideoFrameRate;
    int mVideoBitRate;
    int mDropFrameFrequency;
    int mVideoWidth;
    int mVideoHeigh;
    int mVideoEncorder;
    int mVideoProfile;

    String mUrl;

    public KsyRecordClientConfig(Builder builder) {
        mCameraType = builder.mCameraType;
        mVoiceType = builder.mVoiceType;
        mAudioSampleRate = builder.mAudioSampleRate;
        mAudioBitRate = builder.mAudioBitRate;
        mAudioEncorder = builder.mAudioEncorder;
        mVideoFrameRate = builder.mVideoFrameRate;
        mVideoBitRate = builder.mVideoBitRate;
        mDropFrameFrequency = builder.mDropFrameFrequency;
        mVideoWidth = builder.mVideoWidth;
        mVideoHeigh = builder.mVideoHeigh;
        mVideoEncorder = builder.mVideoEncorder;
        mVideoProfile = builder.mVideoProfile;
        mUrl = builder.mUrl;
    }

    public int getCameraType() {
        return mCameraType;
    }

    public int getVoiceType() {
        return mVoiceType;
    }

    public int getAudioSampleRate() {
        return mAudioSampleRate;
    }

    public int getAudioBitRate() {
        return mAudioBitRate;
    }

    public int getAudioEncorder() {
        return mAudioEncorder;
    }

    public int getVideoFrameRate() {
        return mVideoFrameRate;
    }

    public int getVideoBitRate() {
        return mVideoBitRate;
    }

    public int getDropFrameFrequency() {
        return mDropFrameFrequency;
    }

    public int getVideoWidth() {
        return mVideoWidth;
    }

    public int getVideoHeigh() {
        return mVideoHeigh;
    }

    public int getVideoEncorder() {
        return mVideoEncorder;
    }

    public int getVideoProfile() {
        return mVideoProfile;
    }

    public String getUrl() {
        return mUrl;
    }

    public boolean validateParam() throws KsyRecordException {
        //to do
        return true;
    }

    public void configMediaRecorder(MediaRecorder mediaRecorder, int type) {
        if (mediaRecorder == null) {
            throw new IllegalArgumentException("mediaRecorder is null");
        }
        mediaRecorder.setVideoSource(MediaRecorder.VideoSource.CAMERA);
        if (type == MEDIA_SETP) {
            mediaRecorder.setOutputFormat(MediaRecorder.OutputFormat.THREE_GPP);
        } else if (type == MEDIA_TEMP) {
            mediaRecorder.setOutputFormat(MediaRecorder.OutputFormat.MPEG_4);
        }
        mediaRecorder.setVideoEncoder(mVideoEncorder);
        if (mVideoProfile >= 0) {
            int cameraId = -1;
            int numberOfCameras = Camera.getNumberOfCameras();
            if (numberOfCameras > 0) {
                Camera.CameraInfo cameraInfo = new Camera.CameraInfo();
                for (int i = 0; i < numberOfCameras; i++) {
                    Camera.getCameraInfo(i, cameraInfo);
                    if (cameraInfo.facing == mCameraType) {
                        cameraId = i;
                        break;
                    }
                }
            }
            if (cameraId < 0) {
                throw new IllegalArgumentException("camera unsupported quality level");
            }
            if (CamcorderProfile.hasProfile(cameraId, mVideoProfile)) {
                CamcorderProfile camcorderProfile = CamcorderProfile.get(cameraId, mVideoProfile);
                mediaRecorder.setVideoFrameRate(camcorderProfile.videoFrameRate);
                mediaRecorder.setVideoEncodingBitRate(camcorderProfile.videoBitRate);
                mediaRecorder.setVideoSize(camcorderProfile.videoFrameWidth, camcorderProfile.videoFrameHeight);
            }
        }
        if (mVideoBitRate > 0) {
            mediaRecorder.setVideoEncodingBitRate(mVideoBitRate);
        }
        if (mVideoWidth > 0 && mVideoHeigh > 0) {
            mediaRecorder.setVideoSize(mVideoWidth, mVideoHeigh);
        }
    }


    public void setVideoProfile(int profileID) {
        this.mVideoProfile = profileID;

    }

    public static class Builder {

        private int mCameraType = Constants.CAMERA_TYPE_BACK;
        private int mVoiceType = Constants.VOICE_TYPE_MIC;
        private int mAudioSampleRate = 44100;
        private int mAudioBitRate = 32000;
        private int mAudioEncorder = MediaRecorder.AudioEncoder.AAC;
        private int mVideoFrameRate = 30;
        private int mVideoBitRate = 5000000;
        private int mDropFrameFrequency = 0;
        private int mVideoWidth;
        private int mVideoHeigh;
        private int mVideoEncorder = MediaRecorder.VideoEncoder.H264;
        private int mVideoProfile = -1;
        private String mUrl;

        public KsyRecordClientConfig build() {
            return new KsyRecordClientConfig(this);
        }

        public String getmUrl() {
            return mUrl;
        }

        public Builder setUrl(String mUrl) {
            this.mUrl = mUrl;
            return this;
        }

        public int getCameraType() {
            return mCameraType;
        }

        public Builder setCameraType(int mCameraType) {
            this.mCameraType = mCameraType;
            return this;
        }

        public int getVoiceType() {
            return mVoiceType;
        }

        public Builder setVoiceType(int mVoiceType) {
            this.mVoiceType = mVoiceType;
            return this;
        }

        public int getAudioSampleRate() {
            return mAudioSampleRate;
        }

        public Builder setAudioSampleRate(int mAudioSampleRate) {
            this.mAudioSampleRate = mAudioSampleRate;
            return this;
        }

        public int getAudioBitRate() {
            return mAudioBitRate;
        }

        public Builder setAudioBitRate(int mAudioBitRate) {
            this.mAudioBitRate = mAudioBitRate;
            return this;
        }

        public int getAudioEncorder() {
            return mAudioEncorder;
        }

        public Builder setAudioEncorder(int mAudioEncorder) {
            this.mAudioEncorder = mAudioEncorder;
            return this;
        }

        public int getVideoFrameRate() {
            return mVideoFrameRate;
        }

        public Builder setVideoFrameRate(int mVideoFrameRate) {
            this.mVideoFrameRate = mVideoFrameRate;
            return this;
        }

        public int getVideoBitRate() {
            return mVideoBitRate;
        }

        public Builder setVideoBitRate(int mVideoBitRate) {
            this.mVideoBitRate = mVideoBitRate;
            return this;
        }

        public int getDropFrameFrequency() {
            return mDropFrameFrequency;
        }

        public Builder setDropFrameFrequency(int mDropFrameFrequency) {
            this.mDropFrameFrequency = mDropFrameFrequency;
            return this;
        }

        public int getVideoWidth() {
            return mVideoWidth;
        }

        public Builder setVideoWidth(int mVideoWidth) {
            this.mVideoWidth = mVideoWidth;
            return this;
        }

        public int getVideoHeigh() {
            return mVideoHeigh;
        }

        public Builder setVideoHeigh(int mVideoHeigh) {
            this.mVideoHeigh = mVideoHeigh;
            return this;
        }

        public int getVideoEncorder() {
            return mVideoEncorder;
        }

        public Builder setVideoEncorder(int mVideoEncorder) {
            this.mVideoEncorder = mVideoEncorder;
            return this;
        }

        public int getVideoProfile() {
            return mVideoProfile;
        }

        public Builder setVideoProfile(int mVideoProfile) {
            this.mVideoProfile = mVideoProfile;
            return this;
        }
    }


}
