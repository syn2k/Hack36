def main():
    !pip install tensorflow keras numpy mnist matplotlib
    import numpy as np
    import mnist
    import matplotlib.pyplot as plt
    from keras.models import Sequential
    from keras.layers import Dense
    from keras.utils import to_categorical
    import tensorflow as tf
    (train_images, train_labels), (test_images, test_labels) = tf.keras.datasets.mnist.load_data()
    # print(x_train)
    # x_train.shape
    train_images=mnist.train_images()
    train_labels=mnist.train_labels()
    test_images=mnist.test_images()
    test_labels=mnist.test_labels()
    train_images=(train_images/255)-0.5
    test_images=(test_images/255)-0.5

    train_images=train_images.reshape((-1,784))
    test_images=test_images.reshape((-1,784))
    # print(train_images.shape)
    # print(test_images.shape)

    #model 
    model = Sequential()
    model.add(Dense(64,activation = 'relu', input_dim=784))
    model.add(Dense(64,activation = 'relu'))
    model.add(Dense(10,activation = 'softmax'))

    model.compile(optimizer='adam',loss='categorical_crossentropy',metrics=['accuracy'])

    # print(train_images.shape)
    model.fit(train_images,to_categorical(train_labels),epochs=5,batch_size=32)

    model.evaluate(test_images,to_categorical(test_labels))
    for i in range(5):
        img=test_images[i]
        img=np.array(img,dtype='float')
        pixels=img.reshape((28,28))
        # plt.imshow(pixels)
        # plt.show()

    predictions = model.predict(test_images[:5])
    return "Helllloooooo there!!"
#prediction contains probability of occuring of each digit.
# print(np.argmax(predictions, axis=1))

# from PIL import Image
# from pylab import *
# import PIL.ImageOps   

# img = Image.open('2_pic.jpeg')
 
# img = PIL.ImageOps.invert(img)

# img = img.resize((28,28))
# print(img.size)
# img=array(img)

# if(img.size>784):
#   img=img[0:28,0:28,0:1]
# img=img.reshape((-1,784))
# img=(img-np.min(img))
# img=img/np.max(img)
# img=img*255
# img=np.where(img<100, 0, img)
# img=np.where(img>=100, 255, img)
# print(np.max(img))

# pixels=img.reshape((28,28))
# plt.imshow(pixels)
# plt.show()

# predictions = model.predict(img)
# print(np.argmax(predictions, axis=1))
# print(predictions[0])
# # print(pixels)
# # data = Image.fromarray(pixels)
# # data = data.convert("L")
# # print(type(data))
# # data.save('testerimg41.jpg')

