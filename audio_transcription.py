from pydub import AudioSegment
import io
import os
from google.cloud import speech
import wave
from google.cloud import storage
from google.protobuf import empty_pb2

bucketname = "test_audio_proj"
#setting Google credential
os.environ['GOOGLE_APPLICATION_CREDENTIALS']= 'my_json_file.json'
# create client instance 
client = speech.SpeechClient()

#the path of your audio file
file_name = "audio.wav"
with io.open(file_name, "rb") as audio_file:
    content = audio_file.read()
    audio = speech.RecognitionAudio(content=content)
    
config = speech.RecognitionConfig(
    encoding=speech.RecognitionConfig.AudioEncoding.LINEAR16,
    enable_automatic_punctuation=True,
    audio_channel_count=1,
    sample_rate_hertz=16000,
    language_code="en-US",
)

# Sends the request to google to transcribe the audio
#response = client.recognize(request={"config": config, "audio": audio})

recognition_audio = speech.RecognitionAudio(content=content)

# Create the batch recognition request
request = speech.RecognizeRequest(config=config, audio=recognition_audio)

# Make the batch API call
response = client.recognize(request=request)

# Reads the response
# for result in response.results:
#     print("Transcript: {}".format(result.alternatives[0].transcript))
# Process the response
for result in response.results:
    for alternative in result.alternatives:
        print("Transcript:", alternative.transcript)