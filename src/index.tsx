import { NativeModules, Platform } from 'react-native';

const LINKING_ERROR =
  `The package 'react-native-vibration-effect' doesn't seem to be linked. Make sure: \n\n` +
  Platform.select({ ios: "- You have run 'pod install'\n", default: '' }) +
  '- You rebuilt the app after installing the package\n' +
  '- You are not using Expo managed workflow\n';

const VibrationEffect = NativeModules.VibrationEffect
  ? NativeModules.VibrationEffect
  : new Proxy(
      {},
      {
        get() {
          throw new Error(LINKING_ERROR);
        },
      }
    );

export function canControlAmplitude(): Promise<boolean> {
  return VibrationEffect.canControlAmplitude();
}

export function cancel(): void {
  VibrationEffect.cancel();
}

export function createWaveform(timings: number[], amplitudes: number[], repeat: number) {
  VibrationEffect.createWaveform(timings, amplitudes, repeat);
}
