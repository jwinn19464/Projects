from google.cloud import speech_v1p1beta1 as speech
from google.cloud.speech_v1p1beta1 import enums

# UPDATE PATH TO TEST AUDIO FILE INPUT!!!!!!!!
# UPDATE PATH TO OUTPUT TEXT FILE!!!!!!!!!

def transcribe_audio(input_file, output_file):
    client = speech.SpeechClient()

    # Read the audio file
    with open(input_file, 'rb') as audio_file:
        content = audio_file.read()

    audio = speech.RecognitionAudio(content=content)

    # Configure the speech recognition request
    config = speech.RecognitionConfig(
        encoding=enums.RecognitionConfig.AudioEncoding.LINEAR16,
        sample_rate_hertz=16000,
        language_code='en-US'
    )

    # Send the request to the Speech-to-Text API
    response = client.recognize(config=config, audio=audio)

    # Extract the transcriptions from the response
    transcriptions = [result.alternatives[0].transcript for result in response.results]

    # Save the transcriptions to a text file
    with open(output_file, 'w') as text_file:
        for transcription in transcriptions:
            text_file.write(transcription + '\n')

    print('Transcription saved to', output_file)

# Provide the path to your audio file and output text file
input_audio_file = 'path/to/input/audio.wav'
output_text_file = 'path/to/output/transcription.txt'

# Call the function to transcribe the audio
transcribe_audio(input_audio_file, output_text_file)
